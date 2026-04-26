package bank_system;

import java.util.Locale;

/**
 * Ngoại lệ tung ra khi số tiền yêu cầu giao dịch không hợp lệ (ví dụ: số âm).
 */
public class InvalidFundingAmountException extends BankException {
    public InvalidFundingAmountException(double amount) {
        super(String.format(Locale.US, "Số tiền không hợp lệ: $%.2f", amount));
    }
}