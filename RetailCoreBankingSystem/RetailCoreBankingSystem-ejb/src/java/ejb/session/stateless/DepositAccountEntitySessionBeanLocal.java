/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.DepositAccount;
import entity.DepositAccountTransaction;
import javax.ejb.Local;

/**
 *
 * @author jeromegoh
 */
@Local
public interface DepositAccountEntitySessionBeanLocal {
    public long createNewDepositAccount(DepositAccount depositAccount);
    public long createNewDepositAccountTransactio(DepositAccountTransaction depositAccountTransaction);
}
