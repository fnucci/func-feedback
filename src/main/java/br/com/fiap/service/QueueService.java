package br.com.fiap.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@ApplicationScoped
public class QueueService {

    private SqsClient sqsClient;

    @ConfigProperty(name="sqs.queue.url")
    private String queueUrl;

    public QueueService() {
        this.sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public void sendMessage(String messageBody) {
        sqsClient.sendMessage(m -> m
                .queueUrl(queueUrl)
                .messageBody(messageBody)
        );
    }

    public Message recieveMessage() {
        List<Message> messages = sqsClient.receiveMessage(r -> r
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
        ).messages();

        if (messages.isEmpty()) {
            return null;
        }
        sqsClient.deleteMessage(d -> d.queueUrl(queueUrl)
                .receiptHandle(messages.get(0).receiptHandle()));
        return messages.get(0);
    }
}
