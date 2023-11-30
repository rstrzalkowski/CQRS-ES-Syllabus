package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolClassCreatedEvent {
    private UUID id;
    private UUID levelId;
    private UUID teacherId;
    private String name;
    private String fullName;
}
