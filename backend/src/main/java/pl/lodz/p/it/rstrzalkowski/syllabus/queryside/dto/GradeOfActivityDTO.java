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
public class GradeOfActivityDTO {

    private String studentFirstName;

    private String studentLastName;

    private String studentPersonalId;

    private UUID studentId;

    private UUID activityId;

    private Integer grade;

    private String comment;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public GradeOfActivityDTO(UserEntity student, UUID activityId) {
        this.studentId = student.getId();
        this.studentFirstName = student.getFirstName();
        this.studentLastName = student.getLastName();
        this.studentPersonalId = student.getPersonalId();
        this.activityId = activityId;
    }
}
