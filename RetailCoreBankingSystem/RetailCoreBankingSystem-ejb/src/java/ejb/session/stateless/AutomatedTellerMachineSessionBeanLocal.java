/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;

/**
 *
 * @author jeromegoh
 */
@Local
public interface AutomatedTellerMachineSessionBeanLocal {
    public long verifyAtmCard(String atmCardNumber, String pin);
    public void updatePin(long atmCardId, String newPin);
}
