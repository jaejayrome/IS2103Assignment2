/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jeromegoh
 */
@Local
public interface AtmCardEntitySessionBeanLocal {
    public long makeNewAtmCard(List<String> depositAccountNumbers, String nameOnCard, String pin);
    public AtmCard getAtmCardByCustomerId(long customerId);
    public long replaceNewAtmCard(long atmCardId);
    public long verfiyAtmCard(String atmCardNumber, String pin);
    public void updatePin(long atmCardId, String newPin);
}
