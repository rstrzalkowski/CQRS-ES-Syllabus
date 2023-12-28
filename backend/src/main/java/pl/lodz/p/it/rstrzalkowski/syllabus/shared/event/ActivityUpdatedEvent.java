package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityUpdatedEvent {
    private UUID id;
    private Integer weight;
    private LocalDateTime date;
    private String description;
    private String name;
}
