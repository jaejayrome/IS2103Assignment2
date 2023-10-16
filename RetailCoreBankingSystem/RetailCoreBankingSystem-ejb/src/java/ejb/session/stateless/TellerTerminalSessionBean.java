/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class TellerTerminalSessionBean implements TellerTerminalSessionBeanRemote, TellerTerminalSessionBeanLocal {
     @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    private static CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    private static DepositAccountEntitySessionBeanLocal depositAccountEntitySessionBeanLocal;
    
    @Override
    public long createNewCustomer(Customer customer) {
        return customerEntitySessionBeanLocal.createNewCustomer(customer);
    }
    
    public long openNewDepositAccount(DepositAccount depositAccount, int initialDepositAmount) {
        // first part is to open account 
        long l = depositAccountEntitySessionBeanLocal.createNewDepositAccount(depositAccount);
        // account is created
        // provide cash transaction
        return l;
    }
}
