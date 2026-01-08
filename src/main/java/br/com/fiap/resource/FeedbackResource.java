package br.com.fiap.resource;

import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.presenter.NotaBaixaPresenter;
import br.com.fiap.service.FeedbackService;
import br.com.fiap.service.QueueService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.quarkus.arc.Arc;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeedbackResource implements RequestHandler<FeedbackDTO, FeedbackResponse> {

    @Inject
    QueueService queueService;

    @Override
    public FeedbackResponse handleRequest(FeedbackDTO requestDTO, Context context) {
        var logger = context.getLogger();
        logger.log("Processing feedback submission.");

        FeedbackService feedbackService = Arc.container()
                .instance(FeedbackService.class)
                .get();

        var response = feedbackService.cadastrarFeedback(requestDTO);

        if (response.grade() < 5) {
            log.info("Nota menor que 5 detectada. Enviando mensagem para a fila SQS.");
            queueService.sendMessage(NotaBaixaPresenter.toResponse(response).toString());
        }

        return response;
    }
}
