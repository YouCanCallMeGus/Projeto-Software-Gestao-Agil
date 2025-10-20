package br.edu.insper.goals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/goal/{id}")
    public Goal getGoal(@AuthenticationPrincipal Jwt jwt, @PathVariable int id){
        Goal goal = goalService.getGoal(id);
        String email = jwt.getClaimAsString("https://stocks-insper.com/email");

        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (goal.getId() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return goal;
    }

    @PostMapping("/goal")
    public Goal postGoal(@AuthenticationPrincipal Jwt jwt, @RequestBody Goal goal) {
        String email = jwt.getClaimAsString("https://stocks-insper.com/email");

        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        goalService.postGoal(goal);
        return goal;
    }

    @PutMapping("/goal/{id}")
    public Goal putGoal(@AuthenticationPrincipal Jwt jwt, @PathVariable int id, @RequestBody Goal updatedGoal) {
        String email = jwt.getClaimAsString("https://stocks-insper.com/email");

        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (updatedGoal.getDescription() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return goalService.updateGoal(id, updatedGoal);
    }

    @DeleteMapping("/goal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Goal deleteGoal(@AuthenticationPrincipal Jwt jwt, @PathVariable int id) {
        String email = jwt.getClaimAsString("https://stocks-insper.com/email");

        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return goalService.removeGoal(id);
    }
}