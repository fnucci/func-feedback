package br.com.fiap.resource;

import br.com.fiap.model.in.AlunoDTO;
import br.com.fiap.model.out.AlunoResponse;
import br.com.fiap.service.AlunoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/alunos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlunosResource {

    @Inject
    AlunoService alunoService;

    @POST
    public Response cadastrarAluno(AlunoDTO alunoDTO) {

        AlunoResponse response = alunoService.cadastrarAluno(alunoDTO);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();


    }
}
