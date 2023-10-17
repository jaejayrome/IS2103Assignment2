/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tellerterminalclient;

import ejb.session.stateless.TellerTerminalSessionBeanRemote;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
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
        System.out.println("Welcome to Jerome's Bank");
        System.out.println("Do You Have An Existing Account?");
        System.out.println("If yes, press 1. Else, press 2.");
        System.out.print("> ");
        boolean haveExistingAccount = scanner.nextInt() == 1 ? true : false;
        long customerId = -1;
        if (haveExistingAccount) {
            System.out.print("Enter Your Identification Number: ");
            String identificationNumber = scanner.next();
            customerId = tellerTerminalSessionBean.getCustomer(identificationNumber);
        } else {
            System.out.println("Make New Customer");
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
            Customer customer = new Customer(firstName, lastName, identificationNumber, contactNumber, address1, address2, postalCode);
            customerId = tellerTerminalSessionBean.createNewCustomer(customer);
        }
        
        System.out.println("Choose the following actions");
        System.out.println("Press 1 to Create New Deposit Account");
        System.out.println("Press 2 to Issue New ATM Card");
        System.out.println("Press 3 to Issue Replacement ATM Card");
        System.out.print("> ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: 
                System.out.print("Initial Deposit Amount ");
                int amount = scanner.nextInt();
                makeDepositAccount(customerId, amount);
                break;
            case 2: 
             
            case 3:
        }
    }
    
    public static void makeDepositAccount(long customerId, int amount) {
        tellerTerminalSessionBean.openNewDepositAccount(customerId, amount);
        System.out.println("Account Successfully Created!");
    }
    
}
