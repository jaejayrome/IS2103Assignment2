/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import java.math.BigDecimal;
import javax.ejb.Local;
import util.enumeration.TransactionStatus;
import util.enumeration.TransactionType;

/**
 *
 * @author jeromegoh
 */
@Local
public interface DepositAccountEntitySessionBeanLocal {
    public long createNewDepositAccount(long customerId);
    public long createNewDepositAccountTransaction(BigDecimal amount, TransactionType type, String reference, TransactionStatus status, long depositAccountId);
    public long updateDepositAccount(BigDecimal amount, long depositAccountId);
}
