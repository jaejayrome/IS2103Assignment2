/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeromegoh
 */
@Remote
public interface TellerTerminalSessionBeanRemote {
    public long createNewCustomer(Customer customer);
    public long openNewDepositAccount(long customerId, int initialDepositAmount);
    public List<DepositAccount> getDepositAccounts(long customerId);
    public long getCustomer(String identificationNumber);
    public long makeNewAtmCard(List<String> depositAccoutNumbers, String nameOnCard, String pin);
    public AtmCard getAtmCardByCustomerId(long customerId);
    public long replaceNewAtmCard(long atmCardId);
}
