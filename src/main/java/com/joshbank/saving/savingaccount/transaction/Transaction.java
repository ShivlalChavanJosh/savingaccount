package com.joshbank.saving.savingaccount.transaction;


import com.joshbank.saving.savingaccount.user.User;
import com.joshbank.saving.savingaccount.utils.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;

    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public UUID getTid() {
        return id;
    }

    public void setTid(UUID tid) {
        this.id = tid;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getCustomer() {
        return user;
    }

    public void setCustomer(User customer) {
        this.user = customer;
    }



}
