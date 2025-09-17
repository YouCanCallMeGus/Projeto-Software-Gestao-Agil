package br.edu.insper.goals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/goal/{id}")
    public Goal getGoal(@PathVariable int id){
        Goal goal = goalService.getGoal(id);
        if (goal.getId() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return goal;
    }

    @PostMapping("/goal")
    public Goal postGoal(@RequestBody Goal goal) {
        goalService.postGoal(goal);
        return goal;
    }

    @PutMapping("/goal/{id}")
    public Goal putGoal(@PathVariable int id, @RequestBody Goal updatedGoal) {
        if (updatedGoal.getDescription() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return goalService.updateGoal(id, updatedGoal);
    }

    @DeleteMapping("/goal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Goal deleteGoal(@PathVariable int id) {
        return goalService.removeGoal(id);
    }
}