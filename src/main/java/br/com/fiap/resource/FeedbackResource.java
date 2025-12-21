package br.com.fiap.resource;

import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class FeedbackResource {

    @Inject
    FeedbackService feedbackService;

    @FunctionName("cadastrarFeedback")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request
            , final ExecutionContext context) {

        context.getLogger().info("Processing feedback submission.");

        FeedbackDTO feedbackDTO;

        try {
            ObjectMapper mapper = new ObjectMapper();
            feedbackDTO = mapper.readValue(request.getBody().orElse("{}"), FeedbackDTO.class);

        } catch (Exception e) {
            context.getLogger().severe("Error parsing request body: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Invalid feedback data").build();
        }

        FeedbackResponse response = feedbackService.cadastrarFeedback(feedbackDTO);

        return request.createResponseBuilder(HttpStatus.CREATED).body(response).build();
    }
}
