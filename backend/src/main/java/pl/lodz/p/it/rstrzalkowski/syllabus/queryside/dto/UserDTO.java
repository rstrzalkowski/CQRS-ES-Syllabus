package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String email;

    private String firstName;

    private String lastName;

    private String personalId;

    private String description = "";

    private String imageUrl;

    private boolean archived = false;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.personalId = user.getPersonalId();
        this.description = user.getDescription();
        this.archived = user.isArchived();
        this.imageUrl = user.getImageUrl();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
