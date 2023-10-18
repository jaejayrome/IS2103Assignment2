/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatedtellermachineclient;

import ejb.session.stateless.AutomatedTellerMachineSessionBeanRemote;
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
    private static AutomatedTellerMachineSessionBeanRemote automatedTellerMachineSessionBean;

    public static void main(String[] args) {
        printLogo();
        printSubHeader();
        
        Scanner scanner = new Scanner(System.in);
        long atmCardId = insertAtmCard(scanner);
        boolean verified =  atmCardId != -1L;
        int nextChoice = 0;
        
        while (!verified) {
            nextChoice = wrongInput(scanner);
            if (nextChoice == 1) {
                 atmCardId = insertAtmCard(scanner);
                 verified =  atmCardId != -1L;
            }
            if (nextChoice == 0) System.exit(1);
        }
 
        mainMenu(scanner, atmCardId);
        scanner.close();
        
    }
    
    public static void printLogo() {
        String asciiArt =
            ".___  ___.  _______ .______       __       __    ______   .__   __.    .______        ___      .__   __.  __  ___\n" +
            "|   \\/   | |   ____||   _  \\     |  |     |  |  /  __  \\  |  \\ |  |    |   _  \\      /   \\     |  \\ |  | |  |/  /\n" +
            "|  \\  /  | |  |__   |  |_)  |    |  |     |  | |  |  |  | |   \\|  |    |  |_)  |    /  ^  \\    |   \\|  | |  '  /\n" +
            "|  |\\/|  | |   __|  |      /     |  |     |  | |  |  |  | |  . `  |    |   _  <    /  /_\\  \\   |  . `  | |    <\n" +
            "|  |  |  | |  |____ |  |\\  \\----.|  `----.|  | |  `--'  | |  |\\   |    |  |_)  |  /  _____  \\  |  |\\   | |  .  \\\n" +
            "|__|  |__| |_______|| _| `._____||_______||__|  \\______/  |__| \\__|    |______/  /__/     \\__\\ |__| \\__| |__|\\__\\";

        System.out.println(asciiArt);
    }
    
    public static void printSubHeader() {
        String asciiArt =
            "       _____        ______ __    ______         ___ __             __           ___ \n" +
            " /\\ |  ||/  \\|\\/| /\\ ||__ |  \\    ||__ |   |   |__ |__)   |\\/| /\\ /  `|__|||\\ ||__  \n" +
            "/~~\\\\__/|\\__/|  |/~~\\||___|__/    ||___|___|___|___|  \\   |  |/~~\\\\__,|  ||| \\||___ ";

        System.out.println(asciiArt);
    }
    
    public static long insertAtmCard(Scanner scanner) {
            System.out.println("Welcome!");
            System.out.print("Enter Your ATM Card Number: ");
            String cardNumber = scanner.next();
            System.out.print("Enter Your Pin Number: ");
            String pin = scanner.next();
            return automatedTellerMachineSessionBean.verifyAtmCard(cardNumber, pin);
    }
    
    public static int wrongInput(Scanner scanner) {
        System.out.println("You have either used the wrong ATM Card or entered the wrong PIN number");
        System.out.println("Press 0 to exit the system and Press 1 to retry");
        System.out.print("> ");
        return scanner.nextInt();
    }
    
    public static void mainMenu(Scanner scanner, long atmCardId) {
        System.out.println("Choose the following actions");
        System.out.println("Press 1 to change pin number");
        System.out.println("Press 2 to enquire account balance");
        System.out.println("Press 0 to end the session");
        System.out.print("> ");
        int choice = scanner.nextInt();
        
        switch(choice) {
            case 1: 
                updatePinNumber(scanner, atmCardId);
                mainMenu(scanner, atmCardId);
                break;
            case 2: 
                enquireBalance(scanner, atmCardId);
                mainMenu(scanner, atmCardId);
                break;
            case 0: 
                System.exit(1);
                break;
            default:
                break;
        }
    }
    
    public static void updatePinNumber(Scanner scanner, long atmCardId) {
        System.out.print("Enter your new 6 digit PIN Number: ");
        String newNumber = scanner.next();
        automatedTellerMachineSessionBean.updatePin(atmCardId, newNumber);
        System.out.println("Pin Successfully Updated!");
    }
    
    public static void enquireBalance(Scanner scanner, long atmCardId) {
        List<DepositAccount> list = automatedTellerMachineSessionBean.getDepositAccountsFromAtmCard(atmCardId);
        List<String> accountNumberList = list.stream().map(x -> x.getAccountNumber()).collect(Collectors.toList());
        list.forEach(x -> System.out.println("Account Number: " + x.getAccountNumber() + "\n" + "Account Type: " + x.getAccountType().name() + "\n"));
            boolean successfulBalance = false;
            while (!successfulBalance) {
                System.out.print("Enter the account number of account: ");
                String accountNum = scanner.next();
                if (accountNumberList.contains(accountNum)) {
                    DepositAccount account = list.stream().filter(x -> x.getAccountNumber().equals(accountNum)).findFirst().get();
                    System.out.println("-----------------------------------------------------");
                    printDepositAccount(account);
                    successfulBalance = true;
                } else {
                    System.out.println("You entered an invalid deposit account number!");
                    System.out.println("You have either used the wrong ATM Card or entered the wrong PIN number");
                    System.out.println("Press 0 to exit to main menu or Press 1 to retry");
                    System.out.print("> ");
                    if (scanner.nextInt() == 0) {
                        return;
                    } else {
                        continue;
                    }
                }
            }
        
    }

    public static void printDepositAccount(DepositAccount acc) {
         System.out.println("Account Number: " + acc.getAccountNumber());
        System.out.println("Account Type: " + acc.getAccountType().name());
        System.out.println("Available Balance: " + acc.getAvailableBalance().toString());
//        System.out.println("Ledger Balance: " + acc.getLedgerBalance().toString());
//        System.out.println("Hold Balance: " + acc.getHoldBalance().toString());
        System.out.println("-----------------------------------------------------");
    }
    
    public static void printAllDepositAccount(List<DepositAccount> list) {
        for (DepositAccount acc: list) {
           printDepositAccount(acc);
        }
    }
    
}
