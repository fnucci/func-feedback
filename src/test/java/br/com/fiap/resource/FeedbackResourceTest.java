package br.com.fiap.resource;

import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.model.out.FeedbackResponse;
import br.com.fiap.service.FeedbackService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertSame;

public class FeedbackResourceTest {

    @Test
    void handleRequestTest() {
        // arrange
        FeedbackService mockService = Mockito.mock(FeedbackService.class);
        FeedbackResource resource = new FeedbackResource();
        resource.feedbackService = mockService; // package-private field accessible within same package

        FeedbackDTO request = Mockito.mock(FeedbackDTO.class);
        FeedbackResponse expected = Mockito.mock(FeedbackResponse.class);

        Context mockContext = Mockito.mock(Context.class);
        LambdaLogger mockLogger = Mockito.mock(LambdaLogger.class);
        Mockito.when(mockContext.getLogger()).thenReturn(mockLogger);

        Mockito.when(mockService.cadastrarFeedback(request)).thenReturn(expected);

        // act
        FeedbackResponse actual = resource.handleRequest(request, mockContext);

        // assert
        assertSame(expected, actual);
        Mockito.verify(mockContext).getLogger();
        Mockito.verify(mockLogger).log("Processing feedback submission.");
    }
}
