package org.example.helloapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "post")
public class Post extends BaseModel{


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Viewer> viewer;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Attachment> attachments;

    private String description;

    private Date start_time;

    private Date end_time;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private List<Like> likes;

    @Transient
    private String username;

    @Transient
    private String imageUrl;

}
