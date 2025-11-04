package br.edu.insper.summary;

import br.edu.insper.balance.Balance;
import br.edu.insper.balance.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {

    @Autowired
    private BalanceService balanceService;

    public List<Balance> getByMonthAndEmail(int month, String email) {
        List<Balance> balances = balanceService.getBalanceByMonthAndEmail(month, email);
        if (balances.isEmpty()) {
            throw new RuntimeException("error");
        }
        return balances;
    }
}
