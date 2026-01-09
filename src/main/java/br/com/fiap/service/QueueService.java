package br.com.fiap.service;

import br.com.fiap.model.out.FeedbackEmailDTO;
import br.com.fiap.persistence.entity.Feedback;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static io.quarkus.amazon.lambda.runtime.AmazonLambdaMapperRecorder.objectMapper;

@Slf4j
@ApplicationScoped
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

    public void sendMessage(String jsonBody) {
        try {
            log.info("Usando a fila SQS com URL: {}", queueUrl);
            log.info("Enviando mensagem para a fila SQS: {}", jsonBody);

            var resp = sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(jsonBody)
                    .build());

            log.info("Mensagem enviada com sucesso. messageId= {}", resp.messageId());

        } catch (Exception e) {
            log.error("Falha ao enviar mensagem para SQS", e);
            throw e; // importante: se falhar, a Lambda deve falhar e mostrar o erro
        }
    }
}
