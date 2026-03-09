package org.example.helloapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "user_to_user_settings", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "attempt_user_id"}))
public class UserToUserSetting extends BaseModel{
    @ManyToOne
    @JsonBackReference
    private User user;

    @ManyToOne
    @JsonBackReference
    private User attemptUser;

    @Enumerated(EnumType.ORDINAL)
    private BlockStatus blockStatus;
}
