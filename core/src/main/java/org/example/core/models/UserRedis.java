package org.example.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@RedisHash("usersRedis")
public class UserRedis implements Serializable {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    @Indexed
    private String email;
    private String password;
    private int age;
    private int weight;
    private String gender;
    private int training_factor;

    public UserRedis(@JsonProperty("id") String id, @JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname, @JsonProperty("email") String email, @JsonProperty("password") String password, @JsonProperty("age") int age, @JsonProperty("weight") int weight, @JsonProperty("gender") String gender, @JsonProperty("training_factor") int training_factor) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.training_factor = training_factor;
    }
}
