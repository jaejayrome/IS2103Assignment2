/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
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
     @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    private static CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    private static DepositAccountEntitySessionBeanLocal depositAccountEntitySessionBeanLocal;
    
    @Override
    public long createNewCustomer(Customer customer) {
        return customerEntitySessionBeanLocal.createNewCustomer(customer);
    }
    
    @Override
    public long openNewDepositAccount(String identificationNumber, int initialDepositAmount) {
        // open Deposit Account 
        long depositAccountId = depositAccountEntitySessionBeanLocal.createNewDepositAccount(identificationNumber);
        // make initial Deposit
        long depositAccountTransactionId = depositAccountEntitySessionBeanLocal.createNewDepositAccountTransaction(new BigDecimal(initialDepositAmount), TransactionType.DEBIT, "Initial Debit Transaction", TransactionStatus.COMPLETED, depositAccountId);
        // update the amount in the deposit acccount 
        depositAccountEntitySessionBeanLocal.updateDepositAccount(new BigDecimal(initialDepositAmount),depositAccountId);
        return depositAccountId;
    }
    
    
    
     
}
