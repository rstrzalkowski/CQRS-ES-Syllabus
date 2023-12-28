package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.GradeEntity;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {

    private ActivityDTO activityDTO;

    private UUID realisationId;

    private Integer grade;

    private String comment;

    private Timestamp createdAt;

    public GradeDTO(ActivityEntity activity, GradeEntity grade) {
        this.activityDTO = new ActivityDTO(activity);
        this.realisationId = activity.getRealisation().getId();
        if (grade != null) {
            this.grade = grade.getValue();
            this.createdAt = grade.getCreatedAt();
            this.comment = grade.getComment();
        }
    }

    public GradeDTO(GradeEntity grade) {
        this.activityDTO = new ActivityDTO(grade.getActivity());
        this.realisationId = grade.getActivity().getRealisation().getId();
        this.grade = grade.getValue();
        this.createdAt = grade.getCreatedAt();
        this.comment = grade.getComment();
    }
}
