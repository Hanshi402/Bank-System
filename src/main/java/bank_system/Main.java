package bank_system;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer(25021975, "Van Truong Son");
        SavingsAccount savingsAcc = new SavingsAccount(666, 6767.0);
        CheckingAccount checkingAcc = new CheckingAccount(333, 8888.0);
        customer.addAccount(savingsAcc);
        customer.addAccount(checkingAcc);
        System.out.println("\nLogging Testing");
        checkingAcc.deposit(2000.0);
        savingsAcc.withdraw(500.0);
        System.out.println("\nException Testing");
        savingsAcc.withdraw(2000.0);
        checkingAcc.deposit(-500.0);
        System.out.println("\nExport Transaction History");
        System.out.println(checkingAcc.getTransactionHistory());
    }
}