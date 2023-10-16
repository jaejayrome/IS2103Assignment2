/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class CustomerEntitySessionBean implements CustomerEntitySessionBeanLocal {
    
    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public long createNewCustomer(Customer customer) {
        em.persist(customer);
        em.flush();
        System.out.println("hELLO");
        return customer.getCustomerId();
    }
    
     
}
