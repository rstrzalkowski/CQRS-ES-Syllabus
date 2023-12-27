package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityCreatedEvent {
    private UUID id;
    private UUID realisationId;
    private UUID teacherId;
    private Integer weight;
    private LocalDateTime date;
    private String description;
    private String name;
    private Timestamp createdAt;
}
