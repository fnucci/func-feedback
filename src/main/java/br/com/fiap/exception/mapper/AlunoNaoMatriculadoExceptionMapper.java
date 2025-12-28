package br.com.fiap.exception.mapper;

import br.com.fiap.exception.AlunoNaoMatriculadoException;
import br.com.fiap.exception.utils.ApiErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import software.amazon.awssdk.http.HttpStatusCode;

import java.time.LocalDateTime;

@Provider
public class AlunoNaoMatriculadoExceptionMapper implements ExceptionMapper<AlunoNaoMatriculadoException> {
    @Override
    public Response toResponse(AlunoNaoMatriculadoException exception) {

        // Build a custom error response object
        ApiErrorMessage errorResponse = new ApiErrorMessage(
                LocalDateTime.now(),
                HttpStatusCode.NOT_FOUND,
                exception.getMessage(),
                exception.getCause().toString()
        );

        // Return a Response with the desired HTTP status and entity
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .build();
    }

}

