package com.company;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Bank {
   private ArrayList<BankAccount> allAccounts;
   private ArrayList<Customer> allCustomers;

   public Bank(){
       allCustomers = new ArrayList<Customer>();
       allAccounts = new ArrayList<BankAccount>();
   }

   public void doBanking(){
       var inputReader = new Scanner(System.in);
       while(true){
           printMenu();
           var userChoice = inputReader.nextInt();
           switch (userChoice){
               case 1:
                   System.exit(0);
               case 2:
                   addCustomer(inputReader);
                   break;
               case 3:
                   System.out.print("What is the customer ID of the customer to select: ");
                   var idToFind = inputReader.nextInt();
                   var customer = getCustomer(idToFind);
                   if (customer.isPresent()){
                       doCustomerMenu(inputReader, customer.get());
                   }
                   else
                       System.out.println("No such customer exists at this bank");
                   break;
           }
       }
   }

    private void doCustomerMenu(Scanner inputReader, Customer customer) {
       while(true) {
           printCustomerMenu();
           System.out.print("Enter Selection:");
           var choice = inputReader.nextInt();
           switch (choice){
               case 1:
                   BankAccount newAccount = addAccountToCustomer(inputReader, customer);
                   allAccounts.add(newAccount);
                   break;
               case 2:
                   closeAccount(inputReader, customer);

                   break;
               case 3:
                   return;
               default:
                   System.out.println("Please choose one of the choices in the list");
                }
            }
       }

    private void closeAccount(Scanner inputReader, Customer customer) {
       //ask the user for the account number to close
        System.out.println("Enter ID of account to close:");
        var closeID = inputReader.nextInt();
        //call close account on the customer
        var closedAccount = customer.closeAccount(closeID);
        // if successful remove the account from allAccounts as well.
        if(closedAccount.isPresent()){
            System.out.println("Closing account...");
            allAccounts.remove(closedAccount.get());
        }
    }

    private BankAccount addAccountToCustomer(Scanner inputReader, Customer cust) {
        //ask the user how much the initial balance should be
        System.out.println("How much is the initial balance for the new account");
        var initialDeposit = inputReader.nextDouble();
        // call open account in the customer
        var newAccount = cust.openAccount(initialDeposit);
        System.out.println("Created account with ID"+ newAccount.getAccountID());
        //return the newly created account
        return newAccount;
    }

    private void printCustomerMenu() {
       System.out.println("#####################################");
       System.out.println("Please select what to do with this customer");
       System.out.println("  [1] Open an account");
       System.out.println("  [2] Close an account");
       System.out.println("  [3] Return to main menu");
       System.out.println("#####################################");
    }

    private Optional<Customer> getCustomer(int CustomerID){
       for(var currentCustomer : allCustomers){
           if(currentCustomer.getID()==CustomerID );
            return Optional.of(currentCustomer);
       }
       return Optional.empty();
    }

    private void addCustomer(Scanner inputReader) {
       inputReader.nextLine();//eats \n from previous call to nextInt
        System.out.print("Enter the new Customer's name: ");
        var newCustomerName = inputReader.nextLine();
        System.out.print("Enter the new Customer's TaxID (SSN):");
        var newCustomerTaxID = inputReader.nextInt();
        var newCustomer = new Customer(newCustomerName, newCustomerTaxID);
        allCustomers.add(newCustomer);
        System.out.println("Success! Created new Customer with Name: "+ newCustomer.getName() +
                " and taxID: "+ newCustomer.getID());
    }

    private void printMenu() {
       System.out.println("=========================================");
       System.out.println("What do you want to do next:");
       System.out.println("  [1] Exit the program");
       System.out.println("  [2] Add a new Customer");
       System.out.println("  [3] Select Customer");
       System.out.println("==========================================");
       System.out.println("Type the number of options you want:");
    }

}
