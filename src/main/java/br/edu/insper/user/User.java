package br.edu.insper.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.insper.balance.Balance;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String authId;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Balance> balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Balance> getBalance() {
        return balance;
    }

    public void setBalance(List<Balance> balance) {
        this.balance = balance;
    }
}
