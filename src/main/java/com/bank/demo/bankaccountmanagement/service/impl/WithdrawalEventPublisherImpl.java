package com.bank.demo.bankaccountmanagement.service.impl;

import com.bank.demo.bankaccountmanagement.configuration.SNSConfiguration;
import com.bank.demo.bankaccountmanagement.dto.WithdrawalEvent;
import com.bank.demo.bankaccountmanagement.service.WithdrawalEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.math.BigDecimal;

@Service
public class WithdrawalEventPublisherImpl implements WithdrawalEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawalEventPublisherImpl.class);

    private SnsClient snsClient;

    private String snsTopicArn;

    public WithdrawalEventPublisherImpl(SNSConfiguration configuration) {
        this.snsTopicArn = configuration.getTopicArn();
        this.snsClient = SnsClient.builder()
                .region(Region.AP_EAST_1) // Specify your region
                .build();
    }

    @Transactional
    public void publishSuccessfulEvent(BigDecimal amount, Long accountId) {
        logger.info("Publishing successful withdrawal event for account {} and amount of {}", accountId, amount);
        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, "SUCCESSFUL");
        String eventJson = event.toJson();
        PublishRequest publishRequest = PublishRequest.builder()
                .message(eventJson)
                .topicArn(snsTopicArn)
                .build();
        snsClient.publish(publishRequest);
    }

    @Transactional
    public void publishFailedEvent(BigDecimal amount, Long accountId) {
        logger.info("Publishing failed withdrawal event for account {} and amount of {}", accountId, amount);
        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, "FAILED");
        String eventJson = event.toJson();
        PublishRequest publishRequest = PublishRequest.builder()
                .message(eventJson)
                .topicArn(snsTopicArn)
                .build();
        snsClient.publish(publishRequest);
    }
}
