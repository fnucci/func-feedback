package br.com.fiap.service;

import br.com.fiap.model.out.NotaBaixaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;

@Slf4j
@ApplicationScoped
public class QueueService {

    private SqsClient sqsClient;

    @Inject
    private EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public void receiveMessage() {
        List<Message> messages = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
                .waitTimeSeconds(5)
                .build()).messages();

        log.info("Mensagens recebidas da fila: {}", messages.size());

        if (messages.isEmpty()) {
            log.info("Nenhuma mensagem encontrada na fila");
            return;
        }

        try {
            Message message = messages.get(0);
            log.info("Processando mensagem: {}", message.body());

            NotaBaixaResponse notaBaixa = objectMapper.readValue(message.body(), NotaBaixaResponse.class);
            log.info("Mensagem desserializada com sucesso: {}", notaBaixa);

            emailService.send(notaBaixa.email(), "Nota Baixa", notaBaixa.msg());
            log.info("Email enviado com sucesso para: {}", notaBaixa.email());

        } catch (Exception e) {
            log.error("Erro ao processar mensagem da fila SQS: {}", e.getMessage() != null ? e.getCause() : "desconhecida", e);
            throw new RuntimeException(e);
        }
    }
}
