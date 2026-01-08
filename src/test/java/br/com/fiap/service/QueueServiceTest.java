package br.com.fiap.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class QueueServiceTest {

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void receiveMessage_whenNoMessages_doesNothing() throws Exception {
        // arrange
        SqsClient mockSqs = Mockito.mock(SqsClient.class);
        EmailService mockEmailService = Mockito.mock(EmailService.class);
        QueueService service = new QueueService();
        setPrivateField(service, "sqsClient", mockSqs);
        setPrivateField(service, "emailService", mockEmailService);
        setPrivateField(service, "queueUrl", "https://example-queue");

        ReceiveMessageResponse emptyResponse = ReceiveMessageResponse.builder()
                .messages(Collections.emptyList())
                .build();
        when(mockSqs.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(emptyResponse);

        // act
        service.receiveMessage();

        // assert
        verify(mockEmailService, never()).send(any(), any(), any());
    }

    @Test
    void receiveMessage_whenMessagePresent_deserializesAndSendsEmail() throws Exception {
        // arrange
        SqsClient mockSqs = Mockito.mock(SqsClient.class);
        EmailService mockEmailService = Mockito.mock(EmailService.class);
        QueueService service = new QueueService();
        setPrivateField(service, "sqsClient", mockSqs);
        setPrivateField(service, "emailService", mockEmailService);
        setPrivateField(service, "queueUrl", "https://example-queue");

        String jsonBody = "{\"aluno\":\"João Silva\",\"curso\":\"Java\",\"nota\":5,\"msg\":\"Atenção: sua nota está baixa\",\"email\":\"joao@example.com\"}";
        Message msg = Message.builder()
                .messageId("msg-123")
                .body(jsonBody)
                .receiptHandle("rh-123")
                .build();
        ReceiveMessageResponse resp = ReceiveMessageResponse.builder()
                .messages(List.of(msg))
                .build();
        when(mockSqs.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(resp);

        // act
        service.receiveMessage();

        // assert
        verify(mockEmailService, times(1)).send(
                eq("joao@example.com"),
                eq("Nota Baixa"),
                eq("Atenção: sua nota está baixa")
        );
    }

    @Test
    void receiveMessage_whenDeserializationFails_throwsRuntimeException() throws Exception {
        // arrange
        SqsClient mockSqs = Mockito.mock(SqsClient.class);
        EmailService mockEmailService = Mockito.mock(EmailService.class);
        QueueService service = new QueueService();
        setPrivateField(service, "sqsClient", mockSqs);
        setPrivateField(service, "emailService", mockEmailService);
        setPrivateField(service, "queueUrl", "https://example-queue");

        String invalidJsonBody = "invalid json";
        Message msg = Message.builder()
                .messageId("msg-123")
                .body(invalidJsonBody)
                .receiptHandle("rh-123")
                .build();
        ReceiveMessageResponse resp = ReceiveMessageResponse.builder()
                .messages(List.of(msg))
                .build();
        when(mockSqs.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(resp);

        // act & assert
        assertThrows(RuntimeException.class, service::receiveMessage);
        verify(mockEmailService, never()).send(any(), any(), any());
    }
}
