/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class AtmCardEntitySessionBean implements AtmCardEntitySessionBeanLocal {

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBean;

    @EJB
    private DepositAccountEntitySessionBeanLocal depositAccountEntitySessionBean;
    

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    public long makeNewAtmCard(List<String> depositAccountNumbers, String nameOnCard, String pin) {
        AtmCard atmCard = new AtmCard(generateCardNumber(), nameOnCard, true, pin);
        em.persist(atmCard);
        em.flush();
        boolean associateCustomer = false;
        for (String depositAccountNumber : depositAccountNumbers) {
            DepositAccount depositAccount = depositAccountEntitySessionBean.getDepositAccountFromAccountNumber(depositAccountNumber);
            Customer customer = depositAccount.getCustomer();
            int initialise = atmCard.getDepositAccount().size();
            atmCard.getDepositAccount().add(depositAccount);
            depositAccount.setAtmCard(atmCard);
            if (!associateCustomer) {
                atmCard.setAtmCardCustomer(customer);
                customer.setAtmCard(atmCard);
                associateCustomer = true;
            }
            
        }
        return atmCard.getAtmCardId();
    }
    
    @Override
    public AtmCard getAtmCardByCustomerId(long customerId) {
        Customer customer = customerEntitySessionBean.getCustomerById(customerId);
        return customer.getAtmCard();
    }
    
    @Override
    public long replaceNewAtmCard(long atmCardId) {
        // retrive the old atmCard
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);
        // retrieve the customer that was linked previously
        Customer customer = atmCard.getAtmCardCustomer();
        // intialise all the previously linkedAtmCards
        int initialise = atmCard.getDepositAccount().size();
       // find all the accountNumbers for deposit account that are previously linked
        List<String> listOfAccountNumbers = atmCard.getDepositAccount().stream().map(x -> x.getAccountNumber()).collect(Collectors.toList());
        // make the new atm card
        long replacementCardId = this.makeNewAtmCard(listOfAccountNumbers, atmCard.getNameOnCard(), atmCard.getPin());
        // persist the new atm card
        AtmCard replacementCard = em.find(AtmCard.class, replacementCardId);
        
        // unlinking all the previous relationships
        customer.setAtmCard(null);
        
        List<DepositAccount> depositAccountList = atmCard.getDepositAccount();
        for (DepositAccount depositAccount : depositAccountList) {
            depositAccount.setAtmCard(null);
        }
        em.remove(atmCard);

        // setting the relationship
        customer.setAtmCard(replacementCard);
        // setting the new relationship
        for (DepositAccount depositAccount : depositAccountList) {
            depositAccount.setAtmCard(replacementCard);
        }
        
        return replacementCardId;
    }
    
    @Override
    public long verfiyAtmCard(String atmCardNumber, String pin) {
        try {
        AtmCard atmCard = (AtmCard)(em.createQuery("SELECT card FROM AtmCard card WHERE card.cardNumber LIKE :atmCardNumber AND card.pin LIKE :atmPin")
          .setParameter("atmCardNumber", atmCardNumber)
          .setParameter("atmPin", pin)
          .getSingleResult());
        return atmCard.getAtmCardId();
        } catch (NoResultException e) {
            return -1L;
        }
        
    }
    
    @Override
    public void updatePin(long atmCardId, String newPin) {
        em.find(AtmCard.class, atmCardId).setPin(newPin);
    }
    
    public static String generateCardNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }
        return accountNumber.toString();
    }
    
    @Override
    public List<DepositAccount> getDepositAccountsFromAtmCard(long atmCardId) {
        AtmCard atmCard = em.find(AtmCard.class, atmCardId);
        if (atmCard != null) {
            int init = atmCard.getDepositAccount().size();
            return atmCard.getDepositAccount();
        } else return new ArrayList<DepositAccount>();
    }

}
