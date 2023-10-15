/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.DepositAccount;
import entity.DepositAccountTransaction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class DepositAccountEntitySessionBean implements DepositAccountEntitySessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public long createNewDepositAccount(DepositAccount depositAccount) {
        em.persist(depositAccount);
        em.flush();
        return depositAccount.getDepositAccountId();
    }
    
    @Override
    public long createNewDepositAccountTransactio(DepositAccountTransaction depositAccountTransaction) {
        em.persist(depositAccountTransaction);
        em.flush();
        return depositAccountTransaction.getDepositAccountTransactionId();
    }
    
}
