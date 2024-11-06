package com.bank.demo.bankaccountmanagement.service.impl;

import com.bank.demo.bankaccountmanagement.configuration.SNSConfiguration;
import com.bank.demo.bankaccountmanagement.dto.WithdrawalEvent;
import com.bank.demo.bankaccountmanagement.service.WithdrawalEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.math.BigDecimal;

@Service
public class WithdrawalEventPublisherImpl implements WithdrawalEventPublisher {

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
        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, "FAILED");
        String eventJson = event.toJson();
        PublishRequest publishRequest = PublishRequest.builder()
                .message(eventJson)
                .topicArn(snsTopicArn)
                .build();
        snsClient.publish(publishRequest);
    }
}
