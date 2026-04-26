package bank_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho Ngân hàng, quản lý danh sách khách hàng.
 */
public class Bank {
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);
    private static final String ID_REGEX = "\\d{9}";

    private List<Customer> customerList;

    public Bank() {
        this.customerList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Thiết lập danh sách khách hàng.
     *
     * @param customerList Danh sách khách hàng cần thiết lập
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Đọc danh sách khách hàng và tài khoản từ luồng đầu vào.
     *
     * @param inputStream Luồng dữ liệu đầu vào
     */
    public void readCustomerList(InputStream inputStream) {
        logger.info("Bắt đầu đọc dữ liệu khách hàng từ InputStream...");
        if (inputStream == null) {
            logger.warn("InputStream bị null, dừng quá trình đọc.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Customer currentCustomer = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                int lastSpaceIndex = line.lastIndexOf(' ');
                if (lastSpaceIndex <= 0) {
                    continue;
                }

                String token = line.substring(lastSpaceIndex + 1).trim();

                if (token.matches(ID_REGEX)) {
                    String name = line.substring(0, lastSpaceIndex).trim();
                    currentCustomer = new Customer(Long.parseLong(token), name);
                    customerList.add(currentCustomer);
                    logger.debug("Thêm khách hàng thành công: {}", name);
                } else if (currentCustomer != null) {
                    parseAndAddAccount(line, currentCustomer);
                }
            }
        } catch (IOException | NumberFormatException e) {
            logger.error("Đã xảy ra lỗi trong quá trình đọc dữ liệu: {}", e.getMessage(), e);
        }
    }

    private void parseAndAddAccount(String line, Customer customer) {
        String[] parts = line.split("\\s+");
        if (parts.length >= 3) {
            try {
                long accountNumber = Long.parseLong(parts[0]);
                double balance = Double.parseDouble(parts[2]);
                String accountType = parts[1];

                if (Account.CHECKING_TYPE.equals(accountType)) {
                    customer.addAccount(new CheckingAccount(accountNumber, balance));
                } else if (Account.SAVINGS_TYPE.equals(accountType)) {
                    customer.addAccount(new SavingsAccount(accountNumber, balance));
                }
            } catch (NumberFormatException e) {
                logger.warn("Sai định dạng số liệu tài khoản tại dòng: {}", line);
            }
        }
    }

    public String getCustomersInfoByIdOrder() {
        Collections.sort(customerList, Comparator.comparingLong(Customer::getIdNumber));

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < customerList.size(); i++) {
            result.append(customerList.get(i).getCustomerInfo());
            if (i < customerList.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    public String getCustomersInfoByNameOrder() {
        List<Customer> copyList = new ArrayList<>(customerList);
        Collections.sort(copyList, (c1, c2) -> {
            int nameComparison = c1.getFullName().compareTo(c2.getFullName());
            return nameComparison != 0
                    ? nameComparison
                    : Long.compare(c1.getIdNumber(), c2.getIdNumber());
        });

        StringBuilder result = new StringBuilder();
        for (Customer customer : copyList) {
            result.append(customer.getCustomerInfo()).append("\n");
        }
        return result.toString().trim();
    }
}