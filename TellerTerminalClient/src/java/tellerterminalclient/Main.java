/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tellerterminalclient;

import ejb.session.stateless.TellerTerminalSessionBeanRemote;
import entity.Customer;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author jeromegoh
 */
public class Main {

    @EJB
    private static TellerTerminalSessionBeanRemote tellerTerminalSessionBean;
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Customer First Name: ");
        String firstName = scanner.next();
        System.out.print("Customer Last Name: ");
        String lastName = scanner.next();
        System.out.print("Customer Identification Number: ");
        String identificationNumber = scanner.next();
        System.out.print("Customer Contact Number: ");
        String contactNumber = scanner.next();
        System.out.print("Customer Address Line 1: ");
        String address1 = scanner.next();
        System.out.print("Customer Address Line 2: ");
        String address2 = scanner.next();
        System.out.print("Customer Postal Code: ");
        String postalCode = scanner.next();
        
        tellerTerminalSessionBean.createNewCustomer(new Customer(firstName, lastName, identificationNumber, contactNumber, address1, address2, postalCode));
    }
    
}
