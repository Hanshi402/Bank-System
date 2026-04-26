package bank_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {

    @Test
    public void testDeposit() {
        CheckingAccount acc = new CheckingAccount(3636, 1000.0);
        acc.deposit(500.0);

        // Xác minh số dư cuối cùng phải là 1500.0
        assertEquals(9999.0, acc.getBalance(), "Nạp tiền bị sai logic!");
    }
}