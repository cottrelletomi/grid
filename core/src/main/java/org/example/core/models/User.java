package org.example.core.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int age;
    private int weight;
    private char gender;
    private int training_factor;
    private int heartRate;

    public void setFirstname(String firstname) {
        this.firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1).toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
}