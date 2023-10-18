/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jeromegoh
 */
@Stateless
public class AutomatedTellerMachineSessionBean implements AutomatedTellerMachineSessionBeanRemote, AutomatedTellerMachineSessionBeanLocal {

    @EJB
    private AtmCardEntitySessionBeanLocal atmCardEntitySessionBean;

    public long verifyAtmCard(String atmCardNumber, String pin) {
        return atmCardEntitySessionBean.verfiyAtmCard(atmCardNumber, pin);
    } 
    
    @Override
    public void updatePin(long atmCardId, String newPin) {
        atmCardEntitySessionBean.updatePin(atmCardId, newPin);
    }
}
