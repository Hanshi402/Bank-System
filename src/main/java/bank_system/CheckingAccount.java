package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản vãng lai.
 */
public class CheckingAccount extends Account {
    private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);

    public CheckingAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        logger.info("Bắt đầu nạp tiền vào tài khoản vãng lai {}...", getAccountNumber());
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_CHECKING, amount, initialBalance, finalBalance);
            addTransaction(transaction);
            logger.info("Nạp tiền tài khoản vãng lai thành công: +{}", amount);
        } catch (BankException e) {
            logger.error("Lỗi nạp tiền vãng lai: {}", e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        logger.info("Bắt đầu rút tiền từ tài khoản vãng lai {}...", getAccountNumber());
        double initialBalance = getBalance();
        try {
            doWithdrawing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_WITHDRAW_CHECKING, amount, initialBalance, finalBalance);
            addTransaction(transaction);
            logger.info("Rút tiền tài khoản vãng lai thành công: -{}", amount);
        } catch (BankException e) {
            logger.error("Lỗi rút tiền vãng lai: {}", e.getMessage());
        }
    }
}