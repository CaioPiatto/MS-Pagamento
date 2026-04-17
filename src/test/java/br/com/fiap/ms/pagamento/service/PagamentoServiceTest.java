package br.com.fiap.ms.pagamento.service;

import br.com.fiap.ms.pagamento.dto.PagamentoDTO;
import br.com.fiap.ms.pagamento.entities.Pagamento;
import br.com.fiap.ms.pagamento.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.pagamento.repository.PagamentoRepository;
import br.com.fiap.ms.pagamento.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;


    private Long existindId;
    private Long nonExistingId;

    private Pagamento pagamento;

    @BeforeEach
    void SetUp() {
        existindId = 1L;
        nonExistingId = Long.MAX_VALUE;

        pagamento = Factory.createPagamento();
    }

    @Test
    void  deletePagamentoByIdShouldDeleteWhenIdExists(){
        Mockito.when(pagamentoRepository.existsById(existindId)).thenReturn(true);

        pagamentoService.deletePagamentoById(existindId);

        Mockito.verify(pagamentoRepository).existsById(existindId);
        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById((existindId));
    }

    @Test
    @DisplayName("deletePagamentoById deveria lançar ResourceNotFuoundException quando o Id nao existir")
    void deletePagamentoByIdShouldDeleteWhenIdDoesNotExists() {

        Mockito.when(pagamentoRepository.existsById(nonExistingId)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    pagamentoService.deletePagamentoById(nonExistingId);
                });

        Mockito.verify(pagamentoRepository).existsById(nonExistingId);
        Mockito.verify(pagamentoRepository, Mockito.never()).deleteById(Mockito.anyLong());

    }

    @Test
    void findPagamentoByIdShouldReturnPagamentoDTOWhenIdExists() {

        Mockito.when(pagamentoRepository.findById(existindId))
                .thenReturn(Optional.of(pagamento));

        PagamentoDTO result = pagamentoService.findPagamentoById(existindId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());

        Mockito.verify(pagamentoRepository).findById(existindId);
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void findPagamentoByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Mockito.when(pagamentoRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> pagamentoService.findPagamentoById(nonExistingId));

        Mockito.verify(pagamentoRepository).findById(nonExistingId);
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    @DisplayName("Dado parâmetros válidos e Id nulo " +
            "quando chamar Salvar Pagamento " +
            "então deve gerar Id e persistir um Pagamento")
    void givenValidParamsAndIdIsNull_whenSave_thenShouldPersistPagamento() {
        // Arrange
        Mockito.when(pagamentoRepository.save(any(Pagamento.class)))
                .thenReturn(pagamento);

        PagamentoDTO inputDto = new PagamentoDTO(pagamento);

        PagamentoDTO result = pagamentoService.savePagamento(inputDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());

        Mockito.verify(pagamentoRepository).save(any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void updatePagamentoShouldReturnPagamentoDTOWhenIdExists() {
        // Arrange
        Long id = pagamento.getId();
        Mockito.when(pagamentoRepository.getReferenceById(id)).thenReturn(pagamento);
        Mockito.when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        // Act
        PagamentoDTO result = pagamentoService.updatePagamento(id, new PagamentoDTO(pagamento));

        // Assert e Verify
        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());

        Mockito.verify(pagamentoRepository).getReferenceById(id);
        Mockito.verify(pagamentoRepository).save(any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void updatePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Mockito.when(pagamentoRepository.getReferenceById(nonExistingId))
                .thenThrow(EntityNotFoundException.class);

        PagamentoDTO inputDto = new PagamentoDTO(pagamento);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> pagamentoService.updatePagamento(nonExistingId, inputDto));

        Mockito.verify(pagamentoRepository).getReferenceById(nonExistingId);
        Mockito.verify(pagamentoRepository, Mockito.never()).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }




}
