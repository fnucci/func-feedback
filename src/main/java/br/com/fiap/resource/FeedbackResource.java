package br.com.fiap.resource;

import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.service.FeedbackService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/feedbacks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FeedbackResource {

    @Inject
    FeedbackService feedbackService;

    @POST
    public Response cadastrarFeedback(FeedbackDTO feedbackDTO) {

        FeedbackResponse response = feedbackService.cadastrarFeedback(feedbackDTO);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}
