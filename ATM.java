import java.io.*;
import java.util.Scanner;

public class ATM {
    private static final String file = "pin.txt";
    private static final String transactionsFile = "transactions.txt"; 

    public static void main(String args[]) {
        int balance = 0, withdraw, deposit;
        int pin = 0; 
        Scanner sc = new Scanner(System.in);

        try {
            BufferedReader pinReader = new BufferedReader(new FileReader(file));
            String pinString = pinReader.readLine();
            if (pinString != null && !pinString.isEmpty()) {
                pin = Integer.parseInt(pinString);
            }
            pinReader.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Set PIN first!");
        }

        if (pin == 0) {
            System.out.print("Set up your PIN: ");
            pin = sc.nextInt();

            try {
                FileWriter pinWriter = new FileWriter(file);
                pinWriter.write(Integer.toString(pin));
                pinWriter.close();
                System.out.println("PIN set up successfully.");
            } catch (IOException e) {
                System.out.println("Error saving PIN to file.");
            }
        }

        try {
            BufferedReader transactionReader = new BufferedReader(new FileReader(transactionsFile));
            String balanceString = transactionReader.readLine();
            if (balanceString != null && !balanceString.isEmpty()) {
                balance = Integer.parseInt(balanceString);
            }
            transactionReader.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Default balance = 0");
        }

        while (true) {
            System.out.print("Enter your PIN: ");
            int enteredPin = sc.nextInt();

            if (enteredPin != pin) {
                System.out.println("Incorrect PIN. Please try again.");
                continue;
            }

            System.out.println("ATM Machine\n");
            System.out.println("Choose 1 for Deposit");
            System.out.println("Choose 2 for Withdraw");
            System.out.println("Choose 3 for Check Balance");
            System.out.println("Choose 4 for EXIT\n");
            System.out.print("Choose the operation:");

            int choice = sc.nextInt();
            switch (choice) {
                
                case 1:
                    System.out.print("Enter money to be deposited:");
                    deposit = sc.nextInt();
                    balance = balance + deposit;
                    System.out.println("Your Money has been successfully deposited");
                    saveTransaction(balance);
                    System.out.println("");
                    break;

                case 2:
                    System.out.print("Enter money to be withdrawn:");
                    withdraw = sc.nextInt();
                    if (balance >= withdraw) {
                        balance = balance - withdraw;
                        System.out.println("Please collect your money");
                    } else {
                        System.out.println("Insufficient Balance");
                    }
                    saveTransaction(balance);
                    System.out.println("");
                    break;


                case 3:
                    System.out.println("Balance : " + balance);
                    System.out.println("");
                    break;

                case 4:
                    saveTransaction(balance);
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private static void saveTransaction(int balance) {
        try {
            FileWriter transactionWriter = new FileWriter(transactionsFile);
            transactionWriter.write(Integer.toString(balance));
            transactionWriter.close();
            System.out.println("Transaction saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving transaction to file.");
        }
    }
}
