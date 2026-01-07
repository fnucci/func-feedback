package br.com.fiap.resource;

import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.service.FeedbackService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FeedbackResource implements RequestHandler<FeedbackDTO, FeedbackResponse> {

    @Inject
    FeedbackService feedbackService;

    @Override
    public FeedbackResponse handleRequest(FeedbackDTO requestDTO, Context context) {

        LambdaLogger logger = context.getLogger();

        logger.log("Processing feedback submission.");


        return feedbackService.cadastrarFeedback(requestDTO);
    }
}
