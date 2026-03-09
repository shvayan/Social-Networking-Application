package org.example.helloapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "status_viewers")
public class Viewer extends BaseModel{

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User viewer;

    @ManyToOne
    @JsonBackReference
    private Post post;
}
