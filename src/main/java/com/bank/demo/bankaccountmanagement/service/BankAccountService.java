package com.bank.demo.bankaccountmanagement.service;

import com.bank.demo.bankaccountmanagement.dto.WithdrawalResult;

import java.math.BigDecimal;

public interface BankAccountService {
    WithdrawalResult withdraw(Long accountId, BigDecimal amount);
}
