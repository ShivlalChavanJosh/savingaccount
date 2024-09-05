package com.joshbank.saving.savingaccount.user;


import com.joshbank.saving.savingaccount.admin.Admin;
import com.joshbank.saving.savingaccount.admin.AdminRepository;
import com.joshbank.saving.savingaccount.transaction.Transaction;
import com.joshbank.saving.savingaccount.transaction.TransactionRepository;
import com.joshbank.saving.savingaccount.utils.CustomerNotFoundException;
import com.joshbank.saving.savingaccount.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;


    public ResponseEntity<?> register(User customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    public ResponseEntity<User> login(String username, String password) {
        User customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return ResponseEntity.ok(customer);
    }

    public Admin adminLogin(String username,String password){
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (passwordEncoder.matches(password, admin.getPassword())) {
            return admin;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public ResponseEntity<Admin> registerAdmin(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return ResponseEntity.ok(adminRepository.save(admin));
    }

    public ResponseEntity<?> updateProfile(UUID customerId, User updatedCustomer) {
        User customer = customerRepository.findById(customerId)
                .orElse(null);

        if(customer == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Customer not found"));
        }

        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    public ResponseEntity<?> deposit(UUID customerId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Deposit amount must be greater than zero."));
        }

        User customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setBalance(customer.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        transactionRepository.save(transaction);

        return ResponseEntity.ok(customerRepository.save(customer));
    }

    public ResponseEntity<?> withdraw(UUID customerId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Withdrawal amount must be greater than zero."));
        }

        User customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        customer.setBalance(customer.getBalance().subtract(amount));

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        transactionRepository.save(transaction);

        return ResponseEntity.ok(customerRepository.save(customer));
    }

    public ResponseEntity<List<Transaction>> getTransactionHistory(UUID customerId) {
        return ResponseEntity.ok(transactionRepository.findByUser_Id(customerId));
    }
}
