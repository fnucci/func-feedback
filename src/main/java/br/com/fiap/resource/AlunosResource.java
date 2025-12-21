//package br.com.fiap.resource;
//
//import br.com.fiap.model.in.AlunoDTO;
//import br.com.fiap.model.out.AlunoResponse;
//import br.com.fiap.service.AlunoService;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//
//@ApplicationScoped
//@Path("/alunos")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public class AlunosResource {
//
//    @Inject
//    AlunoService alunoService;
//
//    @GET
//    public Response listarAlunos() {
//
//        List<AlunoResponse> response = alunoService.listarAlunos();
//
//        return Response.status(Response.Status.OK)
//                .entity(response)
//                .build();
//    }
//
//    @POST
//    public Response cadastrarAluno(AlunoDTO alunoDTO) {
//
//        AlunoResponse response = alunoService.cadastrarAluno(alunoDTO);
//
//        return Response.status(Response.Status.CREATED)
//                .entity(response)
//                .build();
//
//
//    }
//}
