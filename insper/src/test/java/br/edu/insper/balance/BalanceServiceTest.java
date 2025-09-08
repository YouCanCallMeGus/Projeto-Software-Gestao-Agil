package br.edu.insper.balance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Test
    public void test_saveBalanceShouldCallRepository() {

        // mock
        Balance balance = new Balance();
        balance.setId(1);
        balance.setAmount(100.0);

        // Para métodos que retornam valor, use when().thenReturn()
        // Se save() retorna o objeto salvo, mocke o retorno
        Mockito.when(balanceRepository.save(Mockito.any(Balance.class))).thenReturn(balance);

        // chamada funcao
        balanceService.saveBalance(balance);

        // verificacoes - apenas verifique que o método foi chamado
        Mockito.verify(balanceRepository, Mockito.times(1)).save(balance);

    }

    @Test
    public void test_getBalanceShouldReturnBalance() {

        // mock
        Balance balance = new Balance();
        balance.setId(1);
        balance.setAmount(150.0);
        Mockito.when(balanceRepository.findById(1)).thenReturn(Optional.of(balance));

        // chamada funcao
        Balance resp = balanceService.getBalance(1);

        // verificacoes
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(150.0, resp.getAmount());

    }

    @Test
    public void test_getBalanceShouldReturnNewBalanceWhenNotFound() {

        // mock
        Mockito.when(balanceRepository.findById(999)).thenReturn(Optional.empty());

        // chamada funcao
        Balance resp = balanceService.getBalance(999);

        // verificacoes
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(0, resp.getId());
        Assertions.assertEquals(0.0, resp.getAmount());

    }

    @Test
    public void test_removeBalanceShouldDeleteAndReturnBalance() {

        // mock
        Balance balance = new Balance();
        balance.setId(1);
        balance.setAmount(200.0);
        Mockito.when(balanceRepository.findById(1)).thenReturn(Optional.of(balance));
        // delete() é void, então use doNothing()
        Mockito.doNothing().when(balanceRepository).delete(Mockito.any(Balance.class));

        // chamada funcao
        Balance resp = balanceService.removeBalance(1);

        // verificacoes
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(200.0, resp.getAmount());
        Mockito.verify(balanceRepository, Mockito.times(1)).delete(balance);

    }

    @Test
    public void test_removeBalanceShouldReturnNewBalanceWhenNotFound() {

        // mock
        Mockito.when(balanceRepository.findById(999)).thenReturn(Optional.empty());
        // delete() é void, então use doNothing()
        Mockito.doNothing().when(balanceRepository).delete(Mockito.any(Balance.class));

        // chamada funcao
        Balance resp = balanceService.removeBalance(999);

        // verificacoes
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(0, resp.getId());
        Mockito.verify(balanceRepository, Mockito.times(1)).delete(Mockito.any(Balance.class));

    }

    @Test
    public void test_updateBalanceShouldUpdateAndReturnBalance() {

        // mock
        Balance existingBalance = new Balance();
        existingBalance.setId(1);
        existingBalance.setAmount(100.0);

        Balance updatedBalance = new Balance();
        updatedBalance.setAmount(300.0);

        Balance savedBalance = new Balance();
        savedBalance.setId(1);
        savedBalance.setAmount(300.0);

        Mockito.when(balanceRepository.findById(1)).thenReturn(Optional.of(existingBalance));
        Mockito.when(balanceRepository.save(Mockito.any(Balance.class))).thenReturn(savedBalance);

        // chamada funcao
        Balance resp = balanceService.updateBalance(1, updatedBalance);

        // verificacoes
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(300.0, resp.getAmount());
        Mockito.verify(balanceRepository, Mockito.times(1)).save(Mockito.any(Balance.class));

    }

    @Test
    public void test_updateBalanceShouldThrowExceptionWhenNotFound() {

        // mock
        Balance updatedBalance = new Balance();
        updatedBalance.setAmount(300.0);

        Mockito.when(balanceRepository.findById(999)).thenReturn(Optional.empty());

        // verificacoes
        Assertions.assertThrows(RuntimeException.class
                , () -> balanceService.updateBalance(999, updatedBalance));
        Assertions.assertEquals("Balance not found",
                Assertions.assertThrows(RuntimeException.class
                        , () -> balanceService.updateBalance(999, updatedBalance)).getMessage());

    }
}