package br.com.fiap.exception.mapper;

import br.com.fiap.exception.AlunoJaMatriculadoException;
import br.com.fiap.exception.utils.ApiErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import software.amazon.awssdk.http.HttpStatusCode;

import java.time.LocalDateTime;

@Provider
public class AlunoJaMatriculadoExceptionMapper implements ExceptionMapper<AlunoJaMatriculadoException> {
    @Override
    public Response toResponse(AlunoJaMatriculadoException exception) {

        // Build a custom error response object
        ApiErrorMessage errorResponse = new ApiErrorMessage(
                LocalDateTime.now(),
                HttpStatusCode.BAD_REQUEST,
                exception.getMessage(),
                exception.getCause().toString()
        );

        // Return a Response with the desired HTTP status and entity
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }
}
