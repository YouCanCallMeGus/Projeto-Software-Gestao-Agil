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

        Balance balance = new Balance();
        balance.setId(1);
        balance.setAmount(100.0);

        Mockito.when(balanceRepository.save(Mockito.any(Balance.class))).thenReturn(balance);

        balanceService.saveBalance(balance);

        Mockito.verify(balanceRepository, Mockito.times(1)).save(balance);

    }

    @Test
    public void test_getBalanceShouldReturnBalance() {

        Balance balance = new Balance();
        balance.setId(1);
        balance.setAmount(150.0);
        Mockito.when(balanceRepository.findById(1)).thenReturn(Optional.of(balance));

        Balance resp = balanceService.getBalance(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(150.0, resp.getAmount());

    }

    @Test
    public void test_getBalanceShouldReturnNewBalanceWhenNotFound() {

        Mockito.when(balanceRepository.findById(999)).thenReturn(Optional.empty());

        Balance resp = balanceService.getBalance(999);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(0, resp.getId());
        Assertions.assertEquals(0.0, resp.getAmount());

    }

    @Test
    public void test_removeBalanceShouldDeleteAndReturnBalance() {

        Balance balance = new Balance();
        balance.setId(1);
        balance.setAmount(200.0);
        Mockito.when(balanceRepository.findById(1)).thenReturn(Optional.of(balance));
        Mockito.doNothing().when(balanceRepository).delete(Mockito.any(Balance.class));

        Balance resp = balanceService.removeBalance(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(200.0, resp.getAmount());
        Mockito.verify(balanceRepository, Mockito.times(1)).delete(balance);

    }

    @Test
    public void test_removeBalanceShouldReturnNewBalanceWhenNotFound() {

        Mockito.when(balanceRepository.findById(999)).thenReturn(Optional.empty());
        Mockito.doNothing().when(balanceRepository).delete(Mockito.any(Balance.class));

        Balance resp = balanceService.removeBalance(999);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(0, resp.getId());
        Mockito.verify(balanceRepository, Mockito.times(1)).delete(Mockito.any(Balance.class));

    }

    @Test
    public void test_updateBalanceShouldUpdateAndReturnBalance() {

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

        Balance resp = balanceService.updateBalance(1, updatedBalance);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(300.0, resp.getAmount());
        Mockito.verify(balanceRepository, Mockito.times(1)).save(Mockito.any(Balance.class));

    }

    @Test
    public void test_updateBalanceShouldThrowExceptionWhenNotFound() {

        Balance updatedBalance = new Balance();
        updatedBalance.setAmount(300.0);

        Mockito.when(balanceRepository.findById(999)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class
                , () -> balanceService.updateBalance(999, updatedBalance));
        Assertions.assertEquals("Balance not found",
                Assertions.assertThrows(RuntimeException.class
                        , () -> balanceService.updateBalance(999, updatedBalance)).getMessage());

    }
}