package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolClassUpdatedEvent {
    private UUID id;
    private UUID teacherId;
    private Integer level;
    private String name;
    private String fullName;
}
