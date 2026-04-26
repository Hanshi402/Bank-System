package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm - Thực thi các quy định về rút tiền và nạp tiền có điều kiện.
 */
public class SavingsAccount extends Account {
    private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

    private static final double MAX_WITHDRAW = 1000.0;
    private static final double MIN_BALANCE = 5000.0;

    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        logger.info("Đang xử lý giao dịch nạp tiền cho tài khoản {}...", getAccountNumber());
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(transaction);
            logger.info("Nạp tiền vào tài khoản {} thành công: +{}", getAccountNumber(), amount);
        } catch (BankException e) {
            logger.error("Lỗi nạp tiền vào tài khoản {}: {}", getAccountNumber(), e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            if (amount > MAX_WITHDRAW) {
                throw new InvalidFundingAmountException(amount);
            }
            if (initialBalance - amount < MIN_BALANCE) {
                throw new InsufficientFundsException(amount);
            }

            doWithdrawing(amount);
            double finalBalance = getBalance();

            Transaction transaction = new Transaction(
                    Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(transaction);

            logger.info("[SAVINGS] Rút {} thành công từ tài khoản {}. Số dư còn: {}",
                    amount, getAccountNumber(), finalBalance);
        } catch (BankException e) {
            logger.error("Lỗi rút tiền từ tài khoản {}: {}", getAccountNumber(), e.getMessage());
        }
    }
}