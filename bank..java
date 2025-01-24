import java.util.*;
import java.io.*;

abstract class BankAccount {
    protected String accountHolder;
    protected String accountNumber;
    protected double balance;

    public BankAccount(String accountHolder, String accountNumber, double balance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) throws Exception {
        if (amount > balance) {
            throw new Exception("Insufficient balance.");
        } else if (amount <= 0) {
            throw new Exception("Invalid withdrawal amount.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        }
    }

    public abstract void showAccountDetails();
}

// Savings account class with interest calculation
class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountHolder, String accountNumber, double balance, double interestRate) {
        super(accountHolder, accountNumber, balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        balance += balance * interestRate / 100;
        System.out.println("Interest applied. New Balance: " + balance);
    }

    @Override
    public void showAccountDetails() {
        System.out.println("Savings Account Details:");
        System.out.println("Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Interest Rate: " + interestRate);
    }
}

// Checking account class with overdraft facility
class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountHolder, String accountNumber, double balance, double overdraftLimit) {
        super(accountHolder, accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount > (balance + overdraftLimit)) {
            throw new Exception("Exceeds overdraft limit.");
        } else if (amount <= 0) {
            throw new Exception("Invalid withdrawal amount.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        }
    }

    @Override
    public void showAccountDetails() {
        System.out.println("Checking Account Details:");
        System.out.println("Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Overdraft Limit: " + overdraftLimit);
    }
}

// Interface for bank transactions
interface Transaction {
    void processTransaction(double amount) throws Exception;
}

// Concrete class for deposit transaction
class DepositTransaction implements Transaction {
    private BankAccount account;

    public DepositTransaction(BankAccount account) {
        this.account = account;
    }

    @Override
    public void processTransaction(double amount) {
        account.deposit(amount);
    }
}

// Concrete class for withdrawal transaction
class WithdrawalTransaction implements Transaction {
    private BankAccount account;

    public WithdrawalTransaction(BankAccount account) {
        this.account = account;
    }

    @Override
    public void processTransaction(double amount) throws Exception {
        account.withdraw(amount);
    }
}

// Utility class for handling file operations
class FileHandler {
    private static final String FILE_NAME = "bank_accounts.txt";

    public static void saveAccount(BankAccount account) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(account.getAccountHolder() + "," + account.getAccountNumber() + "," + account.getBalance() + "\n");
            System.out.println("Account saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the account.");
        }
    }

    public static void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                System.out.println("Account Holder: " + details[0] + ", Account Number: " + details[1] + ", Balance: " + details[2]);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading accounts.");
        }
    }
}

// Multi-threading for transaction processing
class TransactionThread extends Thread {
    private Transaction transaction;
    private double amount;

    public TransactionThread(Transaction transaction, double amount) {
        this.transaction = transaction;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            transaction.processTransaction(amount);
        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}

// Main class
public class BankSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating sample accounts
        BankAccount savingsAccount = new SavingsAccount("John Doe", "SAV123", 1000.00, 4.5);
        BankAccount checkingAccount = new CheckingAccount("Jane Smith", "CHK456", 500.00, 1000.00);

        // Showing account details
        savingsAccount.showAccountDetails();
        checkingAccount.showAccountDetails();

        // Performing transactions with multi-threading
        Transaction depositSavings = new DepositTransaction(savingsAccount);
        Transaction withdrawChecking = new WithdrawalTransaction(checkingAccount);

        TransactionThread depositThread = new TransactionThread(depositSavings, 200);
        TransactionThread withdrawThread = new TransactionThread(withdrawChecking, 300);

        depositThread.start();
        withdrawThread.start();

        // Saving account details to file
        FileHandler.saveAccount(savingsAccount);
        FileHandler.saveAccount(checkingAccount);

        // Loading account details from file
        System.out.println("\nLoading accounts from file:");
        FileHandler.loadAccounts();

        scanner.close();
    }
}
