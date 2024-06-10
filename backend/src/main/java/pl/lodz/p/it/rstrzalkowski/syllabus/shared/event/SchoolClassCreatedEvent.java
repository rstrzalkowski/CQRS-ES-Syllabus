package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolClassCreatedEvent {
    private UUID id;
    private Integer level;
    private UUID teacherId;
    private String name;
    private String fullName;
    private Timestamp createdAt;
}
