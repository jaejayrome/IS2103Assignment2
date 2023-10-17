/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.DepositAccount;
import entity.DepositAccountTransaction;
import entity.Customer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import util.enumeration.DepositAccountType;
import util.enumeration.TransactionStatus;
import util.enumeration.TransactionType;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class DepositAccountEntitySessionBean implements DepositAccountEntitySessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public long createNewDepositAccount(long customerId) {
        DepositAccount depositAccount = new DepositAccount(generateRandomBankAccountNumber(), DepositAccountType.SAVINGS, new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), true);
        em.persist(depositAccount);
        em.flush();
        Customer customer = em.find(Customer.class, customerId);
        depositAccount.setCustomer(customer);
        // need add this just to initialise property
        int results = customer.getDepositAccounts().size(); 
        customer.getDepositAccounts().add(depositAccount);
        return depositAccount.getDepositAccountId();
    }
    
    @Override
    public long createNewDepositAccountTransaction(BigDecimal amount, TransactionType type, String reference, TransactionStatus status, long depositAccountId) {
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);

        DepositAccountTransaction depositAccountTransaction = new DepositAccountTransaction(new Date(), type, generateTransactionCode(), reference, amount, status);
        em.persist(depositAccountTransaction);
        em.flush();
        // association
        
        depositAccountTransaction.setDepositAccount(depositAccount);
        depositAccount.getDepositAccountTransactions().add(depositAccountTransaction);
        return depositAccountTransaction.getDepositAccountTransactionId();
    }
    
    @Override
    public long updateDepositAccount(BigDecimal amount, long depositAccountId) {
        DepositAccount depositAccount = em.find(DepositAccount.class, depositAccountId);
        depositAccount.setAvailableBalance(amount);
        depositAccount.setLedgerBalance(amount);
        depositAccount = em.merge(depositAccount);
        return depositAccount.getDepositAccountId();
    }
    
    public static String generateTransactionCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String randomString = String.format("%04d", randomNumber);
        String transactionCode = "TX" + timestamp + randomString;
        return transactionCode;
    }
    
    public static String generateRandomBankAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }
        return accountNumber.toString();
    }
    
    public Customer findCustomerWithIdentificationNumber(String identificationNumber) {
        try {
            Customer customer = (Customer)em.createQuery(
                "SELECT c FROM Customer c WHERE c.identificationNumber LIKE :id")
                .setParameter("id", identificationNumber)
                .getSingleResult();
            return customer;
        } catch(NoResultException ex) {
            System.out.println("You Messed Up!");
            return null;
        }
    }
    
}
