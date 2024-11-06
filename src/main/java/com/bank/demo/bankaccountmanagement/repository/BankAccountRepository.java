package com.bank.demo.bankaccountmanagement.repository;


import java.math.BigDecimal;

public interface BankAccountRepository {

    BigDecimal getCurrentBalance(Long accountId);
    void updateBalance(Long accountId, BigDecimal amount);
}
