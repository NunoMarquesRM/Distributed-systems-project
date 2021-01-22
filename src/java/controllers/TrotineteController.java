package controllers;

import entities.Trotinete;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.TrotineteFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.model.chart.PieChartModel;

@Named("trotineteController")
@SessionScoped
public class TrotineteController implements Serializable {

    private Trotinete current;
    private DataModel items = null;
    @EJB
    private beans.TrotineteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    List<Trotinete> tmp = new ArrayList<>();
    Trotinete tro = new Trotinete();
    
    private PieChartModel pieModel1;
    
    
    public TrotineteController() {
    }

    public Trotinete getSelected() {
        if (current == null) {
            current = new Trotinete();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TrotineteFacade getFacade() {
        return ejbFacade;
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
        current = (Trotinete) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/trotinete/UserEdit";
    }

    public String prepareViewAdmin() {
        current = (Trotinete) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "AdminView";
    }

    public String prepareCreate() {
        current = new Trotinete();
        selectedItemIndex = -1;
        return "/cliente/UserPage";
    }

    public String prepareCreateAdmin() {
        current = new Trotinete();
        selectedItemIndex = -1;
        return "/trotinete/AdminCreate";
    }

    public String create() {
        try {
            int idT = 0;
            if (tro != current) {
                idT = 1 + getFacade().count();
                current.setIdTrotinete(idT);
            }
            getFacade().create(current);
            updateCurrentItem();
            recreateModel();
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String createAdmin() {
        try {
            int idT = 0;
            if (tro != current) {
                idT = getFacade().count();
                current.setIdTrotinete(idT);
            }
            getFacade().create(current);
            updateCurrentItem();
            recreateModel();
            current = new Trotinete();
            selectedItemIndex = -1;
            return "/trotinete/AdminList";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Trotinete) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TrotineteUpdated"));
            return "/trotinete/UserConsultar";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateAdmin() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TrotineteUpdated"));
            return "AdminView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateRequisitar() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TrotineteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Trotinete) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        return "/trotinete/AdminList";
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TrotineteDeleted"));
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

    public String nextRequisitar() {
        getPagination().nextPage();
        recreateModel();
        return "/trotinete/Requisitar";
    }

    public String previousRequisitar() {
        getPagination().previousPage();
        recreateModel();
        return "/trotinete/Requisitar";
    }

    public String nextAdmin() {
        getPagination().nextPage();
        recreateModel();
        return "/trotinete/AdminList";
    }

    public String previousAdmin() {
        getPagination().previousPage();
        recreateModel();
        return "/trotinete/AdminList";
    }

    public String nextTrotinete() {
        getPagination().nextPage();
        recreateModel();
        return "/trotinete/UserConsultar";
    }

    public String previousTrotinete() {
        getPagination().previousPage();
        recreateModel();
        return "/trotinete/UserConsultar";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false, 3);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true, 3);
    }

    public Trotinete getTrotinete(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Trotinete.class)
    public static class TrotineteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TrotineteController controller = (TrotineteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "trotineteController");
            return controller.getTrotinete(getKey(value));
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
            if (object instanceof Trotinete) {
                Trotinete o = (Trotinete) object;
                return getStringKey(o.getIdTrotinete());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Trotinete.class.getName());
            }
        }

    }

    public String back() {
        current = new Trotinete();
        return "/cliente/UserPage";
    }

    public String backAdmin() {
        current = new Trotinete();
        prepareList();
        return "/trotinete/AdminList";
    }

    public List<Trotinete> getTmp() {
        return tmp;
    }

    public String alterarList(String email) {
        tmp = ejbFacade.getTrotineteList(email);
        return "/trotinete/UserCreate";
    }

    public String requisitarT(int id, Date inicio, Date fim) {
        if (current != tro && (inicio != null && fim != null)) {
            current = getTrotinete(id);
            current.setEstado("Requisitada");

            int oldDia = Integer.parseInt(current.getNumeroDias());
            long diff = fim.getTime() - inicio.getTime();
            int dia = (int) (diff / (24 * 60 * 60 * 1000));
            dia += oldDia;
            current.setNumeroDias(String.valueOf(dia));

            getFacade().edit(current);
            recreateModel();
            updateCurrentItem();
        }
        tro = current;
        current = new Trotinete();
        return "/cliente/UserPage";
    }

    public String devolverT(int id) {
        if (current != tro) {
            current = getTrotinete(id);
            current.setEstado("Disponivel");
            
            getFacade().edit(current);
            recreateModel();
            updateCurrentItem();
        }
        tro = current;
        current = new Trotinete();
        return "/cliente/UserPage";
    }
    
    public PieChartModel getPieModel1() {
        createPieModel1();
        return pieModel1;
    }
    
    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        
        pieModel1.set("Trotinete 0", 318);
        pieModel1.set("Trotinete 1", 104);
        pieModel1.set("Trotinete 2", 2);
        pieModel1.set("Trotinete 3", 30);
        pieModel1.set("Trotinete 4", 78);
        pieModel1.set("Trotinete 5", 58);
        pieModel1.set("Trotinete 6", 23);
        pieModel1.set("Trotinete 7", 55);
        pieModel1.set("Trotinete 8", 16);
        
        pieModel1.setTitle("Estatísticas");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }
}
