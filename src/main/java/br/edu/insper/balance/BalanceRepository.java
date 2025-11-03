package br.edu.insper.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    public List<Balance> findByMonth(int month);
    public List<Balance> findByUserEmail(String userEmail);
}
