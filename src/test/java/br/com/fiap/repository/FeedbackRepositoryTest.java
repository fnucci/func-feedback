package br.com.fiap.repository;

import br.com.fiap.persistence.entity.Feedback;
import br.com.fiap.persistence.repository.FeedbackRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FeedbackRepositoryTest {

    @Test
    void class_isAnnotatedWithApplicationScoped() {
        // assert that the repository class has the ApplicationScoped annotation
        ApplicationScoped ann = FeedbackRepository.class.getAnnotation(ApplicationScoped.class);
        assertNotNull(ann, "FeedbackRepository should be annotated with @ApplicationScoped");
    }

    @Test
    void class_implementsPanacheRepository() {
        // assert that FeedbackRepository implements PanacheRepository (generic types erased at runtime)
        assertTrue(PanacheRepository.class.isAssignableFrom(FeedbackRepository.class),
                "FeedbackRepository should implement PanacheRepository");
    }

    @Test
    void mockRepository_persistIsInvoked() {
        // arrange
        FeedbackRepository mockRepo = Mockito.mock(FeedbackRepository.class);
        Feedback mockFeedback = Mockito.mock(Feedback.class);

        // act
        mockRepo.persist(mockFeedback);

        // assert - verify interaction with the mock using Mockito
        verify(mockRepo, times(1)).persist(mockFeedback);
    }
}
