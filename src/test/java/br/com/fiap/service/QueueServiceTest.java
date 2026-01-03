package br.com.fiap.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class QueueServiceTest {

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void sendMessage_callsSqsClient() throws Exception {
        // arrange
        SqsClient mockSqs = Mockito.mock(SqsClient.class);
        QueueService queueService = new QueueService();
        setPrivateField(queueService, "sqsClient", mockSqs);
        setPrivateField(queueService, "queueUrl", "https://example-queue");

        String body = "hello";

        // act
        queueService.sendMessage(body);

        // assert
        ArgumentCaptor<SendMessageRequest> captor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(mockSqs, times(1)).sendMessage((SendMessageRequest) captor.capture());
    }

    @Test
    void recieveMessage_whenNoMessages_returnsNull_and_doesNotDelete() throws Exception {
        // arrange
        SqsClient mockSqs = Mockito.mock(SqsClient.class);
        QueueService service = new QueueService();
        setPrivateField(service, "sqsClient", mockSqs);
        setPrivateField(service, "queueUrl", "https://example-queue");

        ReceiveMessageResponse emptyResponse = ReceiveMessageResponse.builder()
                .messages(Collections.emptyList())
                .build();
        when(mockSqs.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(emptyResponse);

        // act
        Message result = service.recieveMessage();

        // assert
        assertNull(result);
        verify(mockSqs, never()).deleteMessage(any(DeleteMessageRequest.class));
    }

    @Test
    void recieveMessage_whenMessagePresent_returnsMessage_and_deletesIt() throws Exception {
        // arrange
        SqsClient mockSqs = Mockito.mock(SqsClient.class);
        QueueService service = new QueueService();
        setPrivateField(service, "sqsClient", mockSqs);
        setPrivateField(service, "queueUrl", "https://example-queue");

        Message msg = Message.builder()
                .messageId("id")
                .body("body")
                .receiptHandle("rh-123")
                .build();
        ReceiveMessageResponse resp = ReceiveMessageResponse.builder()
                .messages(List.of(msg))
                .build();
        when(mockSqs.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(resp);
        when(mockSqs.deleteMessage(any(DeleteMessageRequest.class))).thenReturn(DeleteMessageResponse.builder().build());

        // act
        Message result = service.recieveMessage();

        // assert
        assertSame(msg, result);
        verify(mockSqs, times(1)).deleteMessage(any(DeleteMessageRequest.class));
    }
}
