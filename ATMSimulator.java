import java.util.Scanner;

public class ATMSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double balance = 1000.0;
        int option;

        // Main loop runs until user selects 0
        while (true) {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Check balance");
            System.out.println("2. Withdraw money");
            System.out.println("3. Deposit money");
            System.out.println("0. Exit");
            System.out.print("Choose action: ");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    // Check balance
                    System.out.printf("Your current balance: %.1f units%n", balance);
                    break;

                case 2:
                    // Withdraw money
                    System.out.print("Enter amount: ");
                    double withdraw = sc.nextDouble();

                    if (withdraw <= 0) {
                        System.out.println("Error: Amount must be positive.");
                    } else if (withdraw % 100 != 0) {
                        System.out.println("Error: Amount must be a multiple of 100.");
                    } else if (withdraw > balance) {
                        System.out.printf("Error: insufficient funds. Available: %.1f%n", balance);
                    } else {
                        balance -= withdraw;
                        System.out.println("Withdrawal successful!");
                        System.out.printf("New balance: %.1f%n", balance);
                    }
                    break;

                case 3:
                    // Deposit money
                    System.out.print("Enter amount: ");
                    double deposit = sc.nextDouble();

                    if (deposit <= 0) {
                        System.out.println("Error: Deposit amount must be positive.");
                    } else {
                        balance += deposit;
                        System.out.println("Deposit successful!");
                        System.out.printf("New balance: %.1f%n", balance);
                    }
                    break;

                case 0:
                    // Exit
                    System.out.println("Thank you for using ATM. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}