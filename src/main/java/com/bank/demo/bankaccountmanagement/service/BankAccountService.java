package com.bank.demo.bankaccountmanagement.service;

import com.bank.demo.bankaccountmanagement.dto.WithdrawalResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface BankAccountService {
    WithdrawalResult withdraw(Long accountId, BigDecimal amount);
}
