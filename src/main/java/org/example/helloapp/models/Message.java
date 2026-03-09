package org.example.helloapp.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "messages")
public class Message extends BaseModel{

    @ManyToOne
    @JsonManagedReference
    private User sender;

    @ManyToOne
    @JsonManagedReference
    private User receiver;

    private String content;

    @Enumerated(EnumType.ORDINAL)
    private ReadStatus readStatus;

    @OneToMany(mappedBy = "messages")
    @JsonManagedReference
    private List<Attachment> attachment;
}
