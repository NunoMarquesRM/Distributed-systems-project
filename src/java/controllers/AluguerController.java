package controllers;

import entities.Aluguer;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.AluguerFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("aluguerController")
@SessionScoped
public class AluguerController implements Serializable {

    private Aluguer current;
    private DataModel items = null;
    @EJB
    private beans.AluguerFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private Aluguer novoAluguer = new Aluguer();
    List<Aluguer> tmp = new ArrayList<>();
    private String email = "";

    public AluguerController() {
    }

    public Aluguer getSelected() {
        if (current == null) {
            current = new Aluguer();
            selectedItemIndex = -1;
        }
        novoAluguer = current;
        return current;
    }

    private AluguerFacade getFacade() {
        return ejbFacade;
    }

    public List<Aluguer> getTmp() {
        return tmp;
    }

    public Aluguer getNovoAluguer() {
        return novoAluguer;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Aluguer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareViewAdmin() {
        current = (Aluguer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "AdminView";
    }

    public String prepareCreate() {
        current = new Aluguer();
        selectedItemIndex = -1;
        return "/cliente/UserPage";
    }

    public String prepareCreateAdmin() {
        current = new Aluguer();
        selectedItemIndex = -1;
        return "AdminCreate";
    }

    public String create() {
        try {
            getFacade().create(current);
            novoAluguer = current;
            updateCurrentItem();
            recreateModel();
            current = new Aluguer();
            return "/trotinete/Requisitar";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String createAdmin() {
        try {
            getFacade().create(current);
            novoAluguer = current;
            updateCurrentItem();
            recreateModel();
            current = new Aluguer();
            return "/aluguer/AdminList";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Aluguer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AluguerUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateAdmin() {
        try {
            getFacade().edit(current);
            return "/aluguer/AdminView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void destroy() {
        current = (Aluguer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        return "/aluguer/AdminList";
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AluguerDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String nextAdmin() {
        getPagination().nextPage();
        recreateModel();
        return "AdminList";
    }

    public String previousAdmin() {
        getPagination().previousPage();
        recreateModel();
        return "AdminList";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false, 1);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true, 1);
    }

    public SelectItem[] getItemsAvailableSelectOneDevolver() {
        return JsfUtil.getSelectItems(ejbFacade.getAluguerList(email), true, 1);
    }

    public Aluguer getAluguer(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Aluguer.class)
    public static class AluguerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AluguerController controller = (AluguerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aluguerController");
            return controller.getAluguer(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Aluguer) {
                Aluguer o = (Aluguer) object;
                return getStringKey(o.getIdAluguer());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Aluguer.class.getName());
            }
        }

    }

    public String backAdmin() {
        current = new Aluguer();
        prepareList();
        return "/aluguer/AdminList";
    }

    public String devolverList(String email) {
        this.email = email;
        if (ejbFacade.getAluguerList(email) == null) {
            return "/cliente/UserPage";
        }
        tmp = ejbFacade.getTrotineteList(email);
        return "/trotinete/UserDevolver";
    }

    public void remove(int k) {
        if (novoAluguer != null) {
            current = (Aluguer) getItems().getRowData();
            novoAluguer = current;
            destroy();
        }
    }

}
