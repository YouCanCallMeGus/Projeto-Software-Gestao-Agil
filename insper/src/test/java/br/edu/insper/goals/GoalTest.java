package br.edu.insper.goals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class GoalTest {
    Goal testGoal = new Goal();

    @Test
    public void test_Description() {
        testGoal.setDescription("test");
        assert testGoal.getDescription().equals("test");
    }

    @Test
    public void test_Start(){
        LocalDate testDate = LocalDate.now();
        testGoal.setStart(testDate);
        assert testGoal.getStart().equals(testDate);
    }

    @Test
    public void test_End(){
        LocalDate testDate = LocalDate.now();
        testGoal.setEnd(testDate);
        assert testGoal.getEnd().equals(testDate);
    }

}
