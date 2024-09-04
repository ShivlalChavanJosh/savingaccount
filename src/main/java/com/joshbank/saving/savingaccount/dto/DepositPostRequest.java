package com.joshbank.saving.savingaccount.dto;

import java.math.BigDecimal;
import java.util.UUID;


public class DepositPostRequest {

  private UUID userId;
  private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
