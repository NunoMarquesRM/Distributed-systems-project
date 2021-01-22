/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Cliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nuno Marques
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "ProjetoSD_Final1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    public List<Cliente> getClientes(){
        return (List<Cliente>) em.createNamedQuery("Cliente.findAll").getResultList();
    }
    
    public Cliente getCliente(Cliente cl){
        List<Cliente> list = em.createNamedQuery("Cliente.login").setParameter("email", cl.getEmail()).setParameter("password", cl.getPassword()).getResultList();
        if(list.isEmpty())
            return null;
        return list.get(0);
    }
    public List<Cliente> getEmailList(String email){
        return (List<Cliente>) em.createNamedQuery("Cliente.findByEmail").setParameter("email", email).getResultList();
    }
}
