package io.eagletech.bankingApplication;

import io.eagletech.bankingApplication.exceptions.BankingApplicationException;
import io.eagletech.bankingApplication.models.Bank;
import io.eagletech.bankingApplication.models.CentralBank;
import io.eagletech.bankingApplication.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainApplication {
    private static CentralBank centralBank;
    private static Bank activeBank;
    public static void main(String[] args) {
        displayHomeMessage();
    }

    private static void displayHomeMessage() {
        String message = """
                Welcome 
                1 -> Central Bank Official Login Here
                2 -> Banks Login Here
                3 -> Customer Login Here
                4 -> Exit Application
                """;
        int userChoice = collectIntegerInput(message);
        switch (userChoice) {
            case 1 -> gotoCentralBankLoginPage();
            case 2 -> gotoBanksLoginPage(); //todo not done yet
            case 3 -> gotoCustomerLoginPage(); //todo not done yet
            case 4 -> System.exit(0);
            default -> {
                display("Incorrect Input, please try again");
                displayHomeMessage();
            }
        }
    }

    private static void gotoCustomerLoginPage() {
    }

    private static void gotoBanksLoginPage() {
        String bankCode = collectStringInput("enter Bank sort code");
        String pin = collectStringInput("enter bank pin");
        var bank= centralBank.findBankByBankCode(bankCode);
        if(pin.equals("1234")) {
            if (bank != null) {
                activeBank = bank;
                gotoBankHomePage();
            } else {
                display("Invalid bank login");
                displayHomeMessage();
            }
        }
        else{
            display("Invalid bank login");
            displayHomeMessage();
        }
    }

    private static void gotoBankHomePage() {
    }

    private static void gotoCentralBankLoginPage() {
        int pin = collectIntegerInput("Enter your pin");
        if (pin == 1234){
            centralBank = CentralBank.createCentralBank();
        }
        gotoCentralBankPage();
    }

    private static void gotoCentralBankPage() {
        String message = """
                Welcome 
                1 -> Register A New Bank
                2 -> View All Banks
                3 -> Verify BVN
                4 -> Logout
                """;
        int userChoice = collectIntegerInput(message);
        switch (userChoice) {
            case 1 -> gotoBankRegistrationPage();
            case 2 -> gotoViewAllBanks();
            case 3 -> gotoBvnVerificationPage();
            case 4 -> displayHomeMessage();
            default -> {
                display("Incorrect Input, please try again");
                gotoCentralBankPage();
            }
        }
    }

    private static void gotoBvnVerificationPage() {
        String bvnNumber = collectStringInput("Enter BVN to validate");
        String accountNumber = collectStringInput("Enter account number");
        if(centralBank.validate(bvnNumber)){
            try {
                Customer customer = centralBank.findCustomerByBvn(bvnNumber);
                for (Account account : customer.getMyAccount()) {
                    if (account.getAccountNumber().equals(accountNumber)) {
                        display("Verified");
                        display(customer.toString());
                        display(account.toString());
                        break;
                    }
                }
                display("invalid BVN");
                gotoCentralBankPage();
            }
            catch (BankingApplicationException ex){
                display(ex.getMessage());
                gotoCentralBankPage();
            }
        }
        else {
            display("invalid BVN number");
            gotoCentralBankPage();

        }    }


    private static void gotoViewAllBanks() {
        //todo to make it tabular
        List<Bank> banks = centralBank.findAllBanks();
        banks.forEach(bank -> {
            display(bank.toString());
        });
    }

    private static void gotoBankRegistrationPage() {
        String bankName = collectStringInput("Enter Bank Name");
        String bankShortName =  collectStringInput("Enter bank Short name");
        Bank bank = centralBank.registerNewBank(bankName, bankShortName);
        display("bank Created successfully");
        display(bank.toString());

        gotoCentralBankPage();


    }

    private static String collectStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        display(prompt);
        return scanner.nextLine();
    }

    private static int collectIntegerInput(String message) {
        display(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();

    }

    private static void display(String message) {
        System.out.println(message);
    }
}
