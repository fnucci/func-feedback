package br.com.fiap.resource;

import br.com.fiap.model.in.CursoDTO;
import br.com.fiap.model.in.MatriculaDTO;
import br.com.fiap.model.out.CursoResponse;
import br.com.fiap.model.out.MatriculaResponse;
import br.com.fiap.service.CursoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/alunos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CursosResource {

    @Inject
    CursoService cursoService;

    @POST
    public Response cadastrarAluno(CursoDTO cursoDTO) {

        CursoResponse response = cursoService.cadastrarCurso(cursoDTO);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    public Response cadastrarAluno(MatriculaDTO matriculaDTO) {

        MatriculaResponse response = cursoService.matricularAluno(matriculaDTO);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}
