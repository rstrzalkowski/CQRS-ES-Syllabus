package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Year;
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
}
