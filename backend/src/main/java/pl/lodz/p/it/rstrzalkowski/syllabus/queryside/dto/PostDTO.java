package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.PostEntity;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private UUID postId;
    private UUID authorId;
    private UUID realisationId;
    private String title;
    private String content;
    private String authorFirstName;
    private String authorLastName;
    private String authorImageUrl;
    private String subjectName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean edited;

    public PostDTO(PostEntity post) {
        this.postId = post.getId();
        this.authorId = post.getTeacher().getId();
        this.content = post.getContent();
        this.authorFirstName = post.getTeacher().getFirstName();
        this.authorLastName = post.getTeacher().getLastName();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.edited = post.isEdited();
        this.title = post.getTitle();
        this.realisationId = post.getRealisation().getId();
        this.subjectName = post.getRealisation().getSubject().getName();
    }
}
