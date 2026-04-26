package bank_system;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho một khách hàng trong hệ thống.
 */
public class Customer {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);

    private long idNumber;
    private String fullName;
    private List<Account> accountList;

    /**
     * Constructor không tham số.
     */
    public Customer() {
        this(0L, "");
    }

    /**
     * Constructor khởi tạo khách hàng với mã định danh và họ tên.
     *
     * @param idNumber Mã định danh (số CMND/CCCD)
     * @param fullName Họ và tên khách hàng
     */
    public Customer(long idNumber, String fullName) {
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.accountList = new ArrayList<>();
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        if (accountList == null) {
            this.accountList = new ArrayList<>();
        } else {
            this.accountList = accountList;
        }
    }

    /**
     * Thêm tài khoản cho khách hàng.
     *
     * @param account Tài khoản cần thêm
     */
    public void addAccount(Account account) {
        if (account == null) {
            logger.warn("Cố gắng thêm một tài khoản null vào khách hàng: {}", fullName);
            return;
        }
        if (!accountList.contains(account)) {
            accountList.add(account);
            logger.debug("Đã thêm tài khoản {} cho khách hàng {}", account.getAccountNumber(), fullName);
        }
    }

    /**
     * Xóa tài khoản khỏi khách hàng.
     *
     * @param account Tài khoản cần xóa
     */
    public void removeAccount(Account account) {
        if (account == null) {
            return;
        }
        if (accountList.remove(account)) {
            logger.debug("Đã xóa tài khoản {} khỏi khách hàng {}", account.getAccountNumber(), fullName);
        }
    }

    /**
     * Trả về chuỗi thông tin của khách hàng.
     *
     * @return Thông tin khách hàng
     */
    public String getCustomerInfo() {
        return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
    }
}