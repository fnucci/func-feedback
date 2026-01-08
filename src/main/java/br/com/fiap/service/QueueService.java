package br.com.fiap.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@ApplicationScoped
@Slf4j
public class QueueService {

    private SqsClient sqsClient;

    @ConfigProperty(name = "sqs.queue.url")
    String queueUrl;

    @PostConstruct
    void init() {
        this.sqsClient = SqsClient.builder()
                .region(Region.SA_EAST_1)
                .build();
    }

    public void sendMessage(String messageBody) {
        try {
            log.info("Enviando mensagem para a fila SQS: {}", messageBody);
            log.info("Usando a fila SQS com URL: {}", queueUrl);

            var resp = sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build());

            log.info("Mensagem enviada com sucesso. messageId= {}", resp.messageId());

        } catch (Exception e) {
            log.error("Falha ao enviar mensagem para SQS", e);
            throw e; // importante: se falhar, a Lambda deve falhar e mostrar o erro
        }
    }
}
