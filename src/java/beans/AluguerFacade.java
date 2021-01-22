/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Aluguer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nuno Marques
 */
@Stateless
public class AluguerFacade extends AbstractFacade<Aluguer> {

    @PersistenceContext(unitName = "ProjetoSD_Final1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AluguerFacade() {
        super(Aluguer.class);
    }

    public List<Aluguer> getTrotineteList(String email) {
        List<Aluguer> list = em.createNamedQuery("Aluguer.findByEmail").setParameter("email", email).getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<Aluguer> getAluguerList(String email) {
        List<Aluguer> list = em.createNamedQuery("Aluguer.findByEmail").setParameter("email", email).getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public Aluguer getAluguerId(Aluguer a) {
        List<Aluguer> list = em.createNamedQuery("Aluguer.findByIdAluguer").setParameter("idAluguer", a.getIdAluguer()).getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
