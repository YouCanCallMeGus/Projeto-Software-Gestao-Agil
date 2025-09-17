package br.edu.insper.goals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class GoalControllerTest {

    @InjectMocks
    private GoalController goalController;

    @Mock
    private GoalService goalService;

    @Test
    public void test_getGoalShouldReturnGoal() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setDescription("Viagem");

        Mockito.when(goalService.getGoal(1)).thenReturn(goal);

        Goal resp = goalController.getGoal(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals("Viagem", resp.getDescription());
    }

    @Test
    public void test_getGoalShouldThrowNotFound() {
        Goal goal = new Goal();
        goal.setId(0); // ID = 0 indica não encontrado
        Mockito.when(goalService.getGoal(999)).thenReturn(goal);

        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,
                () -> goalController.getGoal(999));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    public void test_postGoalShouldSaveGoal() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setDescription("Comprar carro");

        Mockito.doNothing().when(goalService).postGoal(Mockito.any(Goal.class));

        Goal resp = goalController.postGoal(goal);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals("Comprar carro", resp.getDescription());

        Mockito.verify(goalService, Mockito.times(1)).postGoal(Mockito.any(Goal.class));
    }

    @Test
    public void test_putGoalShouldUpdateGoal() {
        Goal updatedGoal = new Goal();
        updatedGoal.setId(1);
        updatedGoal.setDescription("Aposentadoria");

        Mockito.when(goalService.updateGoal(1, updatedGoal)).thenReturn(updatedGoal);

        Goal resp = goalController.putGoal(1, updatedGoal);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals("Aposentadoria", resp.getDescription());
    }

    @Test
    public void test_putGoalShouldThrowBadRequestWhenNoDescription() {
        Goal invalidGoal = new Goal();
        invalidGoal.setId(1);
        invalidGoal.setDescription(null);

        ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,
                () -> goalController.putGoal(1, invalidGoal));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    public void test_deleteGoalShouldRemoveGoal() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setDescription("Emergência");

        Mockito.when(goalService.removeGoal(1)).thenReturn(goal);

        Goal resp = goalController.deleteGoal(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals("Emergência", resp.getDescription());
    }
}