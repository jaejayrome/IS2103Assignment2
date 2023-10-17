/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class AtmCardEntitySessionBean implements AtmCardEntitySessionBeanLocal {

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
    
    public static String generateCardNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }
        return accountNumber.toString();
    }

}
