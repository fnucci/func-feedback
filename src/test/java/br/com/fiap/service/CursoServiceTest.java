package br.com.fiap.service;

import br.com.fiap.exception.CursoNotFoundException;
import br.com.fiap.persistence.entity.Curso;
import br.com.fiap.persistence.repository.CursoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CursoServiceTest {

    @Test
    void findCursoById() {
        // arrange
        CursoRepository mockCursoRepository = Mockito.mock(CursoRepository.class);

        Curso curso = Mockito.mock(Curso.class);

        when(mockCursoRepository.findById(1L)).thenReturn(curso);
        when(curso.getName()).thenReturn("Test Course");

        var response = mockCursoRepository.findById(1l);

        // assert
        assertNotNull(response);
        verify(mockCursoRepository, times(1)).findById(anyLong());
    }

    @Test
    void findCursoByIdThrowsException() {
        // arrange
        CursoRepository mockCursoRepository = Mockito.mock(CursoRepository.class);

        when(mockCursoRepository.findById(99L)).thenThrow(CursoNotFoundException.class);

        // assert
        assertThrows(CursoNotFoundException.class, () -> {
            mockCursoRepository.findById(99l);
            ;
        });

        verify(mockCursoRepository, times(1)).findById(anyLong());
    }
}
