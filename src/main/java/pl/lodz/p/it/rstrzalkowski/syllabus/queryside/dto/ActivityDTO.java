package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {

    private UUID activityId;
    private Integer weight;
    private String name;
    private String description;
    private LocalDateTime date;
    private String subjectName;
    private UUID realisationId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ActivityDTO(ActivityEntity activity) {
        this.activityId = activity.getId();
        this.weight = activity.getWeight();
        this.name = activity.getName();
        this.description = activity.getDescription();
        this.date = activity.getDate();
        this.createdAt = activity.getCreatedAt();
        this.updatedAt = activity.getUpdatedAt();
        this.subjectName = activity.getRealisation().getSubject().getName();
        this.realisationId = activity.getRealisation().getId();
    }
}
