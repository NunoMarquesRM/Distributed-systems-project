/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Trotinete;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nuno Marques
 */
@Stateless
public class TrotineteFacade extends AbstractFacade<Trotinete> {

    @PersistenceContext(unitName = "ProjetoSD_Final1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrotineteFacade() {
        super(Trotinete.class);
    }
    
    public List<Trotinete> getTrotineteList(String email){
        List<Trotinete> list = em.createNamedQuery("Trotinete.findByEmail").setParameter("email", email).getResultList();
        if(list.isEmpty())
            return null;
        return list;
    }
}
