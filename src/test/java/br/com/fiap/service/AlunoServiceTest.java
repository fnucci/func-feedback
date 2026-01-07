package br.com.fiap.service;

import br.com.fiap.exception.AlunoNotFoundException;
import br.com.fiap.persistence.entity.Aluno;
import br.com.fiap.persistence.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AlunoServiceTest {

    @Test
    void findAlunoById() {
        // arrange
        AlunoRepository mockAlunoRepository = Mockito.mock(AlunoRepository.class);

        Aluno aluno = Mockito.mock(Aluno.class);

        when(mockAlunoRepository.findById(1L)).thenReturn(aluno);
        when(aluno.getName()).thenReturn("Test Student");

        var response = mockAlunoRepository.findById(1l);

        // assert
        assertNotNull(response);
        verify(mockAlunoRepository, times(1)).findById(anyLong());
    }

    @Test
    void findAlunoByIdThrowsException() {
        // arrange
        AlunoRepository mockAlunoRepository = Mockito.mock(AlunoRepository.class);

        when(mockAlunoRepository.findById(99L)).thenThrow(AlunoNotFoundException.class);

        // assert
        assertThrows(AlunoNotFoundException.class, () -> {
            mockAlunoRepository.findById(99l);;
        });

        verify(mockAlunoRepository, times(1)).findById(anyLong());
    }
}
