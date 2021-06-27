package io.eagletech.bankingApplication;

import io.eagletech.bankingApplication.models.Bank;
import io.eagletech.bankingApplication.models.CentralBank;

import java.util.List;
import java.util.Scanner;

public class MainApplication {
    private static CentralBank centralBank;
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
            case 2 -> gotoBanksLoginPage();
            case 3 -> gotoCustomerLoginPage();
            case 4 -> System.exit(0);
            default -> {
                display("Incorrect Input, please try again");
                displayHomeMessage();
            }
        }
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
                3 -> Delete Bank
                4 -> Logout
                """;
        int userChoice = collectIntegerInput(message);
        switch (userChoice) {
            case 1 -> gotoBankRegistrationPage();
            case 2 -> gotoViewAllBanks();
            case 3 -> gotoDeleteBanks();
            case 4 -> displayHomeMessage();
            default -> {
                display("Incorrect Input, please try again");
                gotoCentralBankPage();
            }
        }
    }

    private static void gotoViewAllBanks() {
        List<Bank> banks = centralBank.findAllBanks();
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
