package com.bank.demo.bankaccountmanagement.service;

import java.math.BigDecimal;

public interface WithdrawalEventPublisher {
    void publishSuccessfulEvent(BigDecimal amount, Long accountId);
    void publishFailedEvent(BigDecimal amount, Long accountId);
}
