package controllers;

import entities.Cliente;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.ClienteFacade;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

@Named("clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    private Cliente current;
    private DataModel items = null;
    @EJB
    private beans.ClienteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    Cliente novoCliente = new Cliente();

    public ClienteController() {
    }

    public Cliente getSelected() {
        if (current == null) {
            current = new Cliente();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ClienteFacade getFacade() {
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
        current = (Cliente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareViewAdmin() {
        current = (Cliente) getItems().getRowData();
        novoCliente = current;
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/cliente/AdminView";
    }

    public String prepareCreate() {
        current = new Cliente();
        selectedItemIndex = -1;
        return "/index";
    }

    public String prepareCreateAdmin() {
        current = new Cliente();
        selectedItemIndex = -1;
        updateCurrentItem();
        return "/cliente/AdminCreate";
    }

    public String create() {
        try {
            // getInstance() method is called with algorithm SHA-1 
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called 
            // to calculate message digest of the input string 
            // returned as array of byte 
            byte[] messageDigest = md.digest(current.getPassword().getBytes());

            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value 
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit 
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            current.setPassword(hashtext);
            current.setNivel(1);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClienteCreated"));
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
            // getInstance() method is called with algorithm SHA-1 
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called 
            // to calculate message digest of the input string 
            // returned as array of byte 
            byte[] messageDigest = md.digest(current.getPassword().getBytes());

            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value 
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit 
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            current.setPassword(hashtext);

            getFacade().create(current);
            current = new Cliente();
            selectedItemIndex = -1;
            updateCurrentItem();
            recreateModel();
            return "/cliente/AdminList";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cliente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClienteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateAdmin() {
        try {
            if (!(current.getPassword().equalsIgnoreCase(novoCliente.getPassword()))) {
                // getInstance() method is called with algorithm SHA-1 
                MessageDigest md = MessageDigest.getInstance("SHA-1");

                // digest() method is called 
                // to calculate message digest of the input string 
                // returned as array of byte 
                byte[] messageDigest = md.digest(current.getPassword().getBytes());

                // Convert byte array into signum representation 
                BigInteger no = new BigInteger(1, messageDigest);

                // Convert message digest into hex value 
                String hashtext = no.toString(16);

                // Add preceding 0s to make it 32 bit 
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
                current.setPassword(hashtext);
            }
            getFacade().edit(current);

            return "/cliente/AdminView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cliente) getItems().getRowData();
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
        recreateModel();
        current = new Cliente();
        return "/cliente/AdminList";
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClienteDeleted"));
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
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false, 2);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true, 2);
    }

    public SelectItem[] getItemsAvailableSelectOneRequisitar() {
        return JsfUtil.getSelectItems(ejbFacade.getEmailList(novoCliente.getEmail()), true, 2);
    }

    public Cliente getCliente(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Cliente.class)
    public static class ClienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClienteController controller = (ClienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clienteController");
            return controller.getCliente(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cliente) {
                Cliente o = (Cliente) object;
                return getStringKey(o.getEmail());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cliente.class.getName());
            }
        }

    }

    public Cliente getNovoCliente() {
        return novoCliente;
    }

    public void setNovoCliente(Cliente novoCliente) {
        this.novoCliente = novoCliente;
    }

    public String login() throws NoSuchAlgorithmException {

        // getInstance() method is called with algorithm SHA-1 
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // digest() method is called 
        // to calculate message digest of the input string 
        // returned as array of byte 
        byte[] messageDigest = md.digest(novoCliente.getPassword().getBytes());

        // Convert byte array into signum representation 
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value 
        String hashtext = no.toString(16);

        // Add preceding 0s to make it 32 bit 
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        novoCliente.setPassword(hashtext);

        novoCliente = ejbFacade.getCliente(novoCliente);

        if (novoCliente != null) {
            if (novoCliente.getNivel() == 1) {

                return "/cliente/UserPage";
            }
            if (novoCliente.getNivel() == 2) {
                return "/cliente/AdminPage";
            }
        }
        novoCliente = new Cliente();
        return "/index";
    }

    public String logout() {
        novoCliente = new Cliente();
        return "/index";
    }

    public String backAdmin() {
        current = new Cliente();
        prepareList();
        return "/cliente/AdminList";
    }
}
