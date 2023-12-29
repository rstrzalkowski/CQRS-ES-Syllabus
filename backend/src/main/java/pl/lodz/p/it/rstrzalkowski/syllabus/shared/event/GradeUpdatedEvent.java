package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeUpdatedEvent {
    private UUID id;
    private UUID activityId;
    private LocalDateTime date;
    private String comment;
    private Integer value;
}
