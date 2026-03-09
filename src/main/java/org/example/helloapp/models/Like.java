package org.example.helloapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity(name = "likes")
public class Like extends BaseModel{

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User viewer;

    @Enumerated(EnumType.ORDINAL)
    private VoteType voteType = VoteType.NONE;

    @ManyToOne
    @JsonBackReference
    private Post post;
}
