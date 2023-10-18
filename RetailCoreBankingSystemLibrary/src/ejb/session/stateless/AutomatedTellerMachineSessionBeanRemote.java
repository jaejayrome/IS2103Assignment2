/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Remote;

/**
 *
 * @author jeromegoh
 */
@Remote
public interface AutomatedTellerMachineSessionBeanRemote {
    public long verifyAtmCard(String atmCardNumber, String pin);
    public void updatePin(long atmCardId, String newPin);
    
}
