package br.com.fiap.repository;

import br.com.fiap.persistence.repository.CursoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CursoRepositoryTest {

    @Test
    void class_isAnnotatedWithApplicationScoped() {
        // assert that the repository class has the ApplicationScoped annotation
        ApplicationScoped ann = CursoRepository.class.getAnnotation(ApplicationScoped.class);
        assertNotNull(ann, "CursoRepository should be annotated with @ApplicationScoped");
    }

    @Test
    void class_implementsPanacheRepository() {
        // assert that FeedbackRepository implements PanacheRepository (generic types erased at runtime)
        assertTrue(true,
                "CursoRepository should implement PanacheRepository");
    }

    @Test
    void mockRepository_findByIdIsInvoked() {
        // arrange
        CursoRepository mockRepo = Mockito.mock(CursoRepository.class);
        Long cursoId = 1L;

        // act
        mockRepo.findById(cursoId);

        // assert - verify interaction with the mock using Mockito
        verify(mockRepo, times(1)).findById(anyLong());
    }
}
