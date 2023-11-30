package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeCreatedEvent {
    private UUID id;
    private UUID activityId;
    private UUID studentId;
    private Integer value;
}
