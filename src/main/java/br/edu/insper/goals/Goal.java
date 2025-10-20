package br.edu.insper.goals;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Goal{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate start = LocalDate.now();
    @Column(nullable = false)
    private LocalDate end = LocalDate.now();

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
