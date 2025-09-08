package br.edu.insper.balance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class BalanceTest {
    Balance testBalance = new Balance();

    @Test
    public void test_Type() {
        testBalance.setType(TransactionType.DEPOSIT);
        assert testBalance.getType().equals(TransactionType.DEPOSIT);
    }

    @Test
    public void test_Description() {
        testBalance.setDescription("teste");
        assert testBalance.getDescription().equals("teste");
    }

    @Test
    public void test_Amount() {
        testBalance.setAmount(10);
        assert testBalance.getAmount() == 10;
    }

    @Test
    public void test_Date() {
        LocalDate testDate = LocalDate.now();
        testBalance.setDate(testDate);
        assert testBalance.getDate() == testDate;
    }
}
