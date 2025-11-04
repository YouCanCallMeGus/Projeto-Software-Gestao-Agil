package br.edu.insper.goals;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer>{
    public List<Goal> findByUserEmail(String userEmail);
}
