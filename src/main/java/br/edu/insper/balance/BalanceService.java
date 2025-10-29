package br.edu.insper.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    public void saveBalance(Balance balance) {
        balanceRepository.save(balance);
    }

    public Balance getBalance(int id) {
        return balanceRepository.findById(id).orElse(new Balance());
    }

    public Balance removeBalance(int id) {
        Balance balance = getBalance(id);
        balanceRepository.delete(balance);
        return balance;
    }

    public Balance updateBalance (int id, Balance updatedBalance) {
        return balanceRepository.findById(id)
                .map(balance -> {
                    balance.setAmount(updatedBalance.getAmount());
                    return balanceRepository.save(balance);
                })
                .orElseThrow(() -> new RuntimeException("Balance not found"));
    }

    public List<Balance> getAllTransactions() {
        return balanceRepository.findAll();
    }

    public double calculateTotalBalance() {
        List<Balance> transactions = balanceRepository.findAll();
        double total = 0.0;
        
        for (Balance transaction : transactions) {
            if (transaction.getType() == TransactionType.DEPOSIT) {
                total += transaction.getAmount();
            } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
                total -= transaction.getAmount();
            }
        }
        
        return total;
    }

    public List<Balance> getBalanceByMonth(int month) {
        return balanceRepository.findByMonth(month);
    }
}
