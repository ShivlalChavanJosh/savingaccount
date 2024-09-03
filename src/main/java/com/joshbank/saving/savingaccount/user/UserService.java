package com.joshbank.saving.savingaccount.user;


import com.joshbank.saving.savingaccount.admin.Admin;
import com.joshbank.saving.savingaccount.admin.AdminRepository;
import com.joshbank.saving.savingaccount.transaction.Transaction;
import com.joshbank.saving.savingaccount.transaction.TransactionRepository;
import com.joshbank.saving.savingaccount.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
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


    public User register(User customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public User login(String username, String password) {
        User customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (passwordEncoder.matches(password, customer.getPassword())) {
            return customer;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public Admin adminLogin(String username,String password){
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (passwordEncoder.matches(password, admin.getPassword())) {
            return admin;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public Admin registerAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    public User updateProfile(UUID customerId, User updatedCustomer) {
        User customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        return customerRepository.save(customer);
    }

    public User deposit(UUID customerId, BigDecimal amount) {
        User customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setBalance(customer.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);

        return customerRepository.save(customer);
    }

    public User withdraw(UUID customerId, BigDecimal amount) {

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
        transactionRepository.save(transaction);

        return customerRepository.save(customer);
    }

    public List<Transaction> getTransactionHistory(UUID customerId) {
        return transactionRepository.findByUser_Id(customerId);
    }
}
