/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Reservation;
import bean.ReservationItem;
import controler.util.SearchUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ayoub
 */
@Stateless
public class ReservationFacade extends AbstractFacade<Reservation> {

    @PersistenceContext(unitName = "PFEPU")
    private EntityManager em;
    @EJB
    ReservationItemFacade reservationItemFacade;

       public List<Reservation> search(Reservation reservation){
        String rq="SELECT r FROM Reservation r WHERE 1=1";
        rq+=SearchUtil.addConstraint("r", "id", "=",reservation.getId());
        rq+=SearchUtil.addConstraint("r", "client.id", "=",reservation.getClient());
        rq+=SearchUtil.addConstraint("r", "dateReservation", "=",reservation.getDateReservation());
        rq+=SearchUtil.addConstraint("r", "prixTotal", "=",reservation.getPrixTotal());
        rq+=SearchUtil.addConstraint("r", "manager.id", "=",reservation.getManager());        
        return em.createQuery(rq).getResultList();
    }

    public void save (Reservation reservation){
        create(reservation);
        Double prixTotal=0D;
        for (int i = 0; i < reservation.getReservationItems().size(); i++) {
            ReservationItem item = reservation.getReservationItems().get(i);
            item.setReservation(reservation);
            reservationItemFacade.create(item);
            prixTotal+=item.getPrixReservation();
        }
        reservation.setPrixTotal(prixTotal);
        edit(reservation);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationFacade() {
        super(Reservation.class);
    }
    
}
