/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.ReservationItem;
import controler.util.SearchUtil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ayoub
 */
@Stateless
public class ReservationItemFacade extends AbstractFacade<ReservationItem> {

    @PersistenceContext(unitName = "PFEPU")
    private EntityManager em;
      public List<ReservationItem> search(ReservationItem reservationItem){
        String rq="SELECT r FROM ReservationItem WHERE 1=1";
        rq+=SearchUtil.addConstraint("r", "id", "=", reservationItem.getId());
        rq+=SearchUtil.addConstraint("r", "dateRetour", "=", reservationItem.getDateRetour());
        rq+=SearchUtil.addConstraint("r", "prixReservation", "=", reservationItem.getPrixReservation());
        return em.createQuery(rq).getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationItemFacade() {
        super(ReservationItem.class);
    }
    
}
