package br.com.fiap.resource;

import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.service.FeedbackService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.quarkus.arc.Arc;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeedbackResource implements RequestHandler<FeedbackDTO, FeedbackResponse> {

    @Override
    public FeedbackResponse handleRequest(FeedbackDTO requestDTO, Context context) {
        var logger = context.getLogger();
        logger.log("Processing feedback submission.");

        FeedbackService feedbackService = Arc.container()
                .instance(FeedbackService.class)
                .get();

        return feedbackService.cadastrarFeedback(requestDTO);
    }
}
