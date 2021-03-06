package io.eagletech.bankingApplication;


import io.eagletech.bankingApplication.database.Storable;
import io.eagletech.bankingApplication.exceptions.InvalidPinException;
import io.eagletech.bankingApplication.exceptions.WithdrawFailedException;
import io.eagletech.bankingApplication.models.AccountType;
import io.eagletech.bankingApplication.models.Customer;
import io.eagletech.bankingApplication.models.Transaction;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account implements Storable {
    @Getter
    private final String accountName;
    private final String accountNumber;
    private final String customerBvn;
    private final String bankName;
    private final AccountType accountType;
    private final List<Transaction> successfulTransactions;
    private int pin;



    public Account(Customer customer, String accountNumber, String bankName, AccountType accountType) {
        this.accountName = customer.getCustomerFirstName() + " " + customer.getCustomerLastName();
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        customerBvn = customer.getBvn();
        this.bankName = bankName;
        successfulTransactions = new ArrayList<>();
        pin = 0;


    }

    @Override
    public String toString() {
        String accountProfile = "";
        accountProfile += "Account Name: " + accountName + "\n";
        accountProfile += "Account Number: " + accountNumber + "\n";
        accountProfile += "Bank Name: " + bankName + "\n";
        accountProfile += "Account Type: " + accountType.toString() + "\n";
        accountProfile += "Customer BVN: " + customerBvn + "\n";
        return accountProfile;

    }

    public BigDecimal calculateAccountBalance() {
        BigDecimal customerCurrentAccountBalance = new BigDecimal(0);
        for(Transaction transaction: successfulTransactions){
            switch (transaction.getTransactionType()){
                case DEBIT, TRANSFER_OUT -> customerCurrentAccountBalance= customerCurrentAccountBalance.subtract(transaction.getTransactionAmount());
                case CREDIT, TRANSFER_IN -> customerCurrentAccountBalance = customerCurrentAccountBalance.add(transaction.getTransactionAmount());
            }

        }
        return customerCurrentAccountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String getId() {
        return accountNumber;
    }

    @Override
    public String getName() {
        return accountName;
    }

    void updatePin(int oldPin, int newPin) {
        if (oldPin == pin) {
            setPin(newPin);
        }
    }

    public void verifyLegibilityForWithdraw(BigDecimal amountToWithdraw, int accountPin) {
        try {
            verifyLegibilityForWithdrawalWith(amountToWithdraw, accountPin);
        } catch (InvalidPinException invalidPinException) {
            throw new WithdrawFailedException(invalidPinException.getMessage());
        }
    }

    private void verifyLegibilityForWithdrawalWith(BigDecimal amountToWithdraw, int accountPin) {
        boolean pinIsNotCorrect = accountPin != getPin();
        if (pinIsNotCorrect)  throw new WithdrawFailedException("Incorrect Pin");
        boolean fundIsInsufficient = amountToWithdraw.compareTo(calculateAccountBalance()) > 0;
        if (fundIsInsufficient) throw new WithdrawFailedException("Insufficient Funds");
    }

    private int getPin() {
        if (pin == 0) throw new InvalidPinException("Pin not Set");
        return pin;
    }

    private void setPin(int newPin) {
        pin = newPin;
    }

    public List<Transaction> getTransaction() {
        return successfulTransactions;
    }

    public void save(Transaction transaction) {
        successfulTransactions.add(transaction);
    }
}
