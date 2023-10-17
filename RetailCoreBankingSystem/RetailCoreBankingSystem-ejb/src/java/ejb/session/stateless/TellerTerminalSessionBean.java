/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.TransactionStatus;
import util.enumeration.TransactionType;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class TellerTerminalSessionBean implements TellerTerminalSessionBeanRemote, TellerTerminalSessionBeanLocal {

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    @EJB
    private DepositAccountEntitySessionBeanLocal depositAccountEntitySessionBeanLocal;
    
     @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public long createNewCustomer(Customer customer) {
        return customerEntitySessionBeanLocal.createNewCustomer(customer);
    }
    
    @Override
    public long openNewDepositAccount(long customerId, int initialDepositAmount) {
        // open Deposit Account 
        long depositAccountId = depositAccountEntitySessionBeanLocal.createNewDepositAccount(customerId);
        // make initial Deposit
        long depositAccountTransactionId = depositAccountEntitySessionBeanLocal.createNewDepositAccountTransaction(new BigDecimal(initialDepositAmount), TransactionType.DEBIT, "Initial Debit Transaction", TransactionStatus.COMPLETED, depositAccountId);
        // update the amount in the deposit acccount 
        depositAccountEntitySessionBeanLocal.updateDepositAccount(new BigDecimal(initialDepositAmount),depositAccountId);
        return depositAccountId;
    }
    
//    @Override
//    public List<DepositAccount> getDepositAccount() {
//        Customer customer = customerEntitySessionBeanLocal.getCustomer(identificationNumber);
//        return customer.getDepositAccounts();
//    }
    
    @Override
    public long getCustomer(String identificationNumber) {
        Customer customer = customerEntitySessionBeanLocal.getCustomer(identificationNumber);
        return customer.getCustomerId();
    }
   
    
    
     
}
