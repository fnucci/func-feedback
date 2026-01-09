package br.com.fiap.service;

import br.com.fiap.exception.AlunoNaoMatriculadoException;
import br.com.fiap.model.in.FeedbackDTO;
import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.entity.Curso;
import br.com.fiap.persistence.entity.Feedback;
import br.com.fiap.persistence.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FeedbackServiceTest {


    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void cadastrarFeedback_whenAlunoNotMatriculado_throwsException() throws Exception {
        // arrange
        AlunoService mockAlunoService = Mockito.mock(AlunoService.class);
        CursoService mockCursoService = Mockito.mock(CursoService.class);
        FeedbackRepository mockFeedbackRepository = Mockito.mock(FeedbackRepository.class);
        QueueService mockQueueService = Mockito.mock(QueueService.class);

        FeedbackService service = new FeedbackService();
        setPrivateField(service, "alunoService", mockAlunoService);
        setPrivateField(service, "cursoService", mockCursoService);
        setPrivateField(service, "feedbackRepository", mockFeedbackRepository);
        setPrivateField(service, "queueService", mockQueueService);

        FeedbackDTO dto = Mockito.mock(FeedbackDTO.class);
        when(dto.getAlunoId()).thenReturn(1L);
        when(dto.getCursoId()).thenReturn(2L);

        Aluno aluno = Mockito.mock(Aluno.class);
        Curso curso = Mockito.mock(Curso.class);
        when(mockAlunoService.findById(1L)).thenReturn(aluno);
        when(mockCursoService.findById(2L)).thenReturn(curso);
        when(curso.getStudents()).thenReturn(Collections.emptyList());

        // act & assert
        assertThrows(AlunoNaoMatriculadoException.class, () -> service.cadastrarFeedback(dto));

        verifyNoInteractions(mockFeedbackRepository);
        verifyNoInteractions(mockQueueService);
    }
}
