/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;

/**
 *
 * @author jeromegoh
 */
@Local
public interface CustomerEntitySessionBeanLocal {
    public long createNewCustomer(Customer customer);
    public Customer getCustomer(String identificationNumber);
    public Customer getCustomerById(long customerId);
}
