/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jeromegoh
 */
@Local
public interface TellerTerminalSessionBeanLocal {
    public long createNewCustomer(Customer customer);
    public long openNewDepositAccount(long customerId, int initialDepositAmount);
    public long getCustomer(String identificationNumber);
    public List<DepositAccount> getDepositAccounts(long customerId);
    public long makeNewAtmCard(List<String> depositAccoutNumbers, String nameOnCard, String pin);
}
