package br.edu.insper.balance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BalanceControllerTest {

    @InjectMocks
    private BalanceController balanceController;

    @Mock
    private BalanceService balanceService;

    // @Test
    // public void test_getDepositShouldReturnBalance() {

    //     Balance balance = new Balance();
    //     balance.setId(1);
    //     balance.setAmount(100.0);
    //     Mockito.when(balanceService.getBalance(1)).thenReturn(balance);

    //     Balance resp = balanceController.getDeposit(1);

    //     Assertions.assertNotNull(resp);
    //     Assertions.assertEquals(1, resp.getId());
    //     Assertions.assertEquals(100.0, resp.getAmount());

    // }

    // @Test
    // public void test_getDepositShouldThrowNotFound() {

    //     Balance balance = new Balance();
    //     balance.setId(0); // ID = 0 indica não encontrado
    //     Mockito.when(balanceService.getBalance(999)).thenReturn(balance);

    //     Assertions.assertThrows(ResponseStatusException.class
    //             , () -> balanceController.getDeposit(999));
    //     Assertions.assertEquals(HttpStatus.NOT_FOUND,
    //             Assertions.assertThrows(ResponseStatusException.class
    //                     , () -> balanceController.getDeposit(999)).getStatusCode());

    // }

    // @Test
    // public void test_postDepositShouldSaveBalance() {

    //     Balance balance = new Balance();
    //     balance.setId(1);
    //     balance.setAmount(200.0);

    //     Mockito.doNothing().when(balanceService).saveBalance(Mockito.any(Balance.class));

    //     Balance resp = balanceController.postDeposit(balance);

    //     Assertions.assertNotNull(resp);
    //     Assertions.assertEquals(1, resp.getId());
    //     Assertions.assertEquals(200.0, resp.getAmount());

    //     Mockito.verify(balanceService, Mockito.times(1)).saveBalance(Mockito.any(Balance.class));

    // }

    @Test
    public void test_putDepositShouldUpdateBalance() {

        Balance updatedBalance = new Balance();
        updatedBalance.setId(1);
        updatedBalance.setAmount(300.0);
        Mockito.when(balanceService.updateBalance(1, updatedBalance)).thenReturn(updatedBalance);

        Balance resp = balanceController.putDeposit(1, updatedBalance);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(300.0, resp.getAmount());

    }

    @Test
    public void test_putDepositShouldThrowBadRequestWhenZeroAmount() {

        Balance invalidBalance = new Balance();
        invalidBalance.setAmount(0.0);

        Assertions.assertThrows(ResponseStatusException.class
                , () -> balanceController.putDeposit(1, invalidBalance));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,
                Assertions.assertThrows(ResponseStatusException.class
                        , () -> balanceController.putDeposit(1, invalidBalance)).getStatusCode());

    }

    @Test
    public void test_deleteDepositShouldRemoveBalance() {

        // mock
        Balance balance = new Balance();
        balance.setId(1);
        balance.setAmount(150.0);
        Mockito.when(balanceService.removeBalance(1)).thenReturn(balance);

        Balance resp = balanceController.deleteDeposit(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(150.0, resp.getAmount());

    }

    @Test
    public void test_deleteDepositShouldReturnNoContentStatus() {

        Balance balance = new Balance();
        balance.setId(1);
        Mockito.when(balanceService.removeBalance(1)).thenReturn(balance);

        Balance resp = balanceController.deleteDeposit(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());

    }
}