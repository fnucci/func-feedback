package br.com.fiap.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@ApplicationScoped
public class QueueService {

    private SqsClient sqsClient;

    @ConfigProperty(name = "sqs.queue.url")
    String queueUrl;

    @ConfigProperty(name = "aws.region", defaultValue = "sa-east-1")
    String awsRegion;

    @PostConstruct
    void init() {
        this.sqsClient = SqsClient.builder()
                .region(Region.of(awsRegion))
                .build();
    }

    public void sendMessage(String messageBody) {
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build());
    }

    public Message receiveMessage() {
        List<Message> messages = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
                .waitTimeSeconds(5) // long polling (melhor)
                .build()).messages();

        if (messages.isEmpty()) return null;

        Message msg = messages.get(0);

        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(msg.receiptHandle())
                .build());

        return msg;
    }
}
