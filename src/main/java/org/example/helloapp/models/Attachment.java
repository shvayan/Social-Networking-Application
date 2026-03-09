package org.example.helloapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "attachments")
public class Attachment extends BaseModel{

    @ManyToOne
    @JsonBackReference
    private Message messages;

    @Enumerated(EnumType.ORDINAL)
    private FileType fileType;

    private String fileUrl;

    @Enumerated(EnumType.ORDINAL)
    private AttachmentType attachmentType;

    @ManyToOne
    @JsonBackReference
    private Post post;


    @PreRemove
    public void fileUnlink(){
        if(this.fileUrl!=null){
            java.io.File file = new java.io.File("uploads/"+this.fileUrl);
            if(file.exists()){
                file.delete();
            }
        }
    }
}
