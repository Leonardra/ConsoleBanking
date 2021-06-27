package io.eagletech.bankingApplication.notification;

import io.eagletech.bankingApplication.Account;
import io.eagletech.bankingApplication.models.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SmsAlert extends Alert {
    private String alertTitle;
    private String amount;
    private String amountInWords;
    private String accountNumber;
    private  String transactionType;
    private String actorName;
    private String availableBalance;
    private String transactionId;
    private String transactionDate;

    public SmsAlert(Account chibuzoAccount, Transaction transaction) {
        super();
        String date = transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a"));
        transactionType = transaction.getTransactionType().toString();
        alertTitle = transactionType + " Notification";
        amount = transaction.getTransactionAmount().toString();
        accountNumber = chibuzoAccount.getAccountNumber();
        actorName = transaction.getActorName();
        transactionDate = date;
        availableBalance = chibuzoAccount.calculateAccountBalance().toString();
        transactionId = transaction.getTransactionId();
        amountInWords = amount;





    }

    @Override
    public String toString() {
        String alert = alertTitle.toUpperCase()+"\n";
        alert+= "TID: "+ transactionId+"\n";
        alert+= "Transaction Date: " + transactionDate +"\n";
        alert+= "Account Number: " + accountNumber+"\n";
        alert+= "Transaction Amount: " + amount+"\n";
        alert+= "Account Name: " + actorName+"\n";
        alert+= "Transaction Type: " + transactionType+"\n";
        alert+= "Available Balance: " + availableBalance+"\n";

        return alert;

    }
}
