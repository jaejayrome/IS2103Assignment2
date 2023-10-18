/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tellerterminalclient;

import ejb.session.stateless.TellerTerminalSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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
        
        boolean programContinues = true;
        while (programContinues) {
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
                    issueNewAtmCard(customerId, scanner);
                    break;

                case 3:
                    issueReplacementAtmCard(customerId, scanner);
                    break;

                default: 
                    break;
            }
            System.out.println("Revert back to main menu?");
            System.out.println("Press 1 to Revert Back");
            System.out.println("Press 0 to Exit The Session");
            System.out.print("> ");
            int finalChoice = scanner.nextInt();
            if (finalChoice == 0) programContinues = false;
        }
    }
    
    // business logic 
    /*
    1) Ensure that deposit account is created
    2) if no account redirect him to create a new deposit account
    3) Else print out the accounts
    4) ask him which accounts that he want to link to, must choose 1
    5) then ask other details then straight away create a reply
    */
    
    public static void issueNewAtmCard(long customerId, Scanner scanner) {
        List<DepositAccount> list = tellerTerminalSessionBean.getDepositAccounts(customerId);
        if (list.isEmpty()) {
            System.out.println("You are required to create a Deposit Account before we can issue an ATM Card.");
        } else {
            printAllDepositAccount(list);
            List<String> accountNumberList = list.stream().map(x -> x.getAccountNumber()).collect(Collectors.toList());
            System.out.print("Enter the number of accounts that you would like to associate this ATM Card with: ");
            int num = scanner.nextInt();
            List<String> accountsToAssociate = new ArrayList<String>();
            for (int i = 1; i <= num; i++) {
                System.out.print("Account Number #" + i + ": ");
                String accountNumber = scanner.next();
                if (accountNumberList.contains(accountNumber)) accountsToAssociate.add(accountNumber);
            }
            
            System.out.print("Enter the name on card: ");
            String name = scanner.nextLine();
            System.out.println("Enter your 6 digit pin: ");
            String pin = scanner.next();
            
            // send to backend the depositaccount id and then from there we can associate with thecustom
            tellerTerminalSessionBean.makeNewAtmCard(accountNumberList, name, pin);
            System.out.println("ATM Card Successfully Issued!");
        }
    }
    
    public static void issueReplacementAtmCard(long customerId, Scanner scanner) {
        // check whether got atm cards or not 
        AtmCard atmCard = tellerTerminalSessionBean.getAtmCardByCustomerId(customerId);
        if (atmCard == null) {
            System.out.println("We can't issue you a replacement card if you never make one before.");
        } else {
            long atmCardId = atmCard.getAtmCardId();
            long newCardId = tellerTerminalSessionBean.replaceNewAtmCard(atmCardId);
            System.out.println("New ATM Card has been replaced!");
        }
    }
    
    public static void makeDepositAccount(long customerId, int amount) {
        tellerTerminalSessionBean.openNewDepositAccount(customerId, amount);
        System.out.println("Account Successfully Created!");
    }
    
    public static void printAllDepositAccount(List<DepositAccount> list) {
        for (DepositAccount acc: list) {
                System.out.println("Account Number: " + acc.getAccountNumber());
                System.out.println("Account Type: " + acc.getAccountType().name());
                System.out.println("Available Balance: " + acc.getAvailableBalance().toString());
                System.out.println("Ledger Balance: " + acc.getLedgerBalance().toString());
                System.out.println("Hold Balance: " + acc.getHoldBalance().toString());
                System.out.println("-----------------------------------------------------");
            }
    }
    
    /*
    Business Logic: 
    ensure that an ATM card has been issued 
    need to issue a replacement atm card that maintains these relationships
    */
    
    // delete must be careful
    
    
}
