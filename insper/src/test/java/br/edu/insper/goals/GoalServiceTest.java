package br.edu.insper.goals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {

    @InjectMocks
    private GoalService goalService;

    @Mock
    private GoalRepository goalRepository;

    @Test
    public void test_saveGoalShouldCallRepository() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setStart(LocalDate.parse("2025-09-13"));
        goal.setEnd(LocalDate.parse("2025-11-11"));
        goal.setDescription("Estou testando o santana");

        Mockito.when(goalRepository.save(Mockito.any(Goal.class))).thenReturn(goal);

        goalService.postGoal(goal);

        Mockito.verify(goalRepository, Mockito.times(1)).save(goal);
    }

    @Test
    public void test_getGoalShouldReturnGoal() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setStart(LocalDate.parse("2025-10-13"));
        goal.setEnd(LocalDate.parse("2025-12-27"));
        goal.setDescription("hehe");
        Mockito.when(goalRepository.findById(1)).thenReturn(Optional.of(goal));

        Goal resp = goalService.getGoal(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(LocalDate.parse("2025-10-13"), resp.getStart());
        Assertions.assertEquals(LocalDate.parse("2025-12-27"), resp.getEnd());
        Assertions.assertEquals("hehe", resp.getDescription());
    }

    @Test
    public void test_getGoalShouldReturnNewGoalWhenNotFound() {
        Mockito.when(goalRepository.findById(999)).thenReturn(Optional.empty());

        Goal resp = goalService.getGoal(999);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(0, resp.getId());
        Assertions.assertEquals(LocalDate.now(), resp.getStart());
        Assertions.assertEquals(LocalDate.now(), resp.getEnd());
        Assertions.assertEquals("", resp.getDescription());
    }


    @Test
    public void test_removeGoalShouldReturnGoal() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setStart(LocalDate.parse("2025-03-13"));
        goal.setEnd(LocalDate.parse("2025-08-27"));
        goal.setDescription("plim plim plim");

        Mockito.when(goalRepository.findById(1)).thenReturn(Optional.of(goal));
        Mockito.doNothing().when(goalRepository).delete(Mockito.any(Goal.class));

        Goal resp = goalService.removeGoal(1);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(LocalDate.parse("2025-03-13"), resp.getStart());
        Assertions.assertEquals(LocalDate.parse("2025-08-27"), resp.getEnd());
        Assertions.assertEquals("plim plim plim", resp.getDescription());
        Mockito.verify(goalRepository, Mockito.times(1)).delete(goal);
    }

    @Test
    public void test_removeGoalShouldReturnNewGoalWhenNotFound() {
        Mockito.when(goalRepository.findById(999)).thenReturn(Optional.empty());
        Mockito.doNothing().when(goalRepository).delete(Mockito.any(Goal.class));

        Goal resp = goalService.removeGoal(999);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(0, resp.getId());
        Assertions.assertEquals(LocalDate.now(), resp.getStart());
        Assertions.assertEquals(LocalDate.now(), resp.getEnd());
        Assertions.assertEquals("", resp.getDescription());
        Mockito.verify(goalRepository, Mockito.times(1)).delete(Mockito.any(Goal.class));
    }


    @Test
    public void test_updateGoalShouldUpdateAndReturnGoal() {
        Goal existingGoal = new Goal();
        existingGoal.setId(1);
        existingGoal.setStart(LocalDate.parse("2025-03-13"));
        existingGoal.setEnd(LocalDate.parse("2025-08-27"));
        existingGoal.setDescription("plim plim plim");

        Goal updateGoal = new Goal();
        updateGoal.setDescription("mudei o bagui kkkkkkk");

        Goal savedGoal = new Goal();
        savedGoal.setId(1);
        savedGoal.setStart(LocalDate.parse("2025-03-13"));
        savedGoal.setEnd(LocalDate.parse("2025-08-27"));
        savedGoal.setDescription("mudei o bagui kkkkkkk");

        Mockito.when(goalRepository.findById(1)).thenReturn(Optional.of(existingGoal));
        Mockito.when(goalRepository.save(Mockito.any(Goal.class))).thenReturn(savedGoal);

        Goal resp = goalService.updateGoal(1, updateGoal);

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.getId());
        Assertions.assertEquals(LocalDate.parse("2025-03-13"), resp.getStart());
        Assertions.assertEquals(LocalDate.parse("2025-08-27"), resp.getEnd());
        Assertions.assertEquals("mudei o bagui kkkkkkk", resp.getDescription());
        Mockito.verify(goalRepository, Mockito.times(1)).save(Mockito.any(Goal.class));
    }

    @Test
    public void test_updateGoalShouldThrowExceptionWhenNotFound() {
        Goal updateGoal = new Goal();
        updateGoal.setDescription("o santana nÃ£o sabe triangulo");

        Mockito.when(goalRepository.findById(999)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class
                , () -> goalService.updateGoal(999, updateGoal));
        Assertions.assertEquals("Goal not found",
                Assertions.assertThrows(RuntimeException.class
                        , () -> goalService.updateGoal(999, updateGoal)).getMessage());
    }
}
