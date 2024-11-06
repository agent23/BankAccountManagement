package com.bank.demo.bankaccountmanagement.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class JdbcBankAccountRepository implements BankAccountRepository{


    private final JdbcTemplate jdbcTemplate;

    public JdbcBankAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BigDecimal getCurrentBalance(Long accountId) {
        String sql = "SELECT balance FROM accounts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, BigDecimal.class);
    }

    public void updateBalance(Long accountId, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, accountId);
    }
}
