package br.edu.insper.balance;

import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/transaction/{id}")
    public Balance getDeposit(@PathVariable Integer id) {
        Balance balance = balanceService.getBalance(id);
        if (balance.getId() != 0) {
            return balance;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public Balance postDeposit(@RequestBody Balance deposit) {
        balanceService.saveBalance(deposit);
        return deposit;
    }

    @PutMapping("/transaction/{id}")
    public Balance putDeposit(@PathVariable Integer id, @RequestBody Balance updatedBalance) {
        if (updatedBalance.getAmount() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return balanceService.updateBalance(id, updatedBalance);
    }

    @DeleteMapping("/transaction/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Balance deleteDeposit(@PathVariable Integer id) {
        return balanceService.removeBalance(id);
    }

    @GetMapping("/transactions")
    public List<Balance> getAllTransactions() {
        return balanceService.getAllTransactions();
    }

    @GetMapping("/balance")
    public Map<String, Double> getTotalBalance() {
        double total = balanceService.calculateTotalBalance();
        Map<String, Double> response = new HashMap<>();
        response.put("balance", total);
        return response;
    }

}
