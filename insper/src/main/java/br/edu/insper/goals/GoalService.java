package br.edu.insper.goals;

import br.edu.insper.balance.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    public Goal getGoal(int id) {return goalRepository.findById(id).orElse(new Goal());}
    public void postGoal(Goal goal) {goalRepository.save(goal);}
    public Goal updateGoal (int id, Goal updatedGoal) {
        return goalRepository.findById(id)
                .map(goal -> {
                    goal.setDescription(updatedGoal.getDescription());
                    goal.setStart(updatedGoal.getStart());
                    goal.setEnd(updatedGoal.getEnd());
                    return goalRepository.save(goal);
                })
                .orElseThrow(() -> new RuntimeException("Goal not found"));
    }
    public Goal removeGoal(int id) {
        Goal goal = getGoal(id);
        goalRepository.delete(goal);
        return goal;
    }
}
