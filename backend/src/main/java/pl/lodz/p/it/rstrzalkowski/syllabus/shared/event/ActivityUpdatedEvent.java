package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ActivityUpdatedEvent {
    private UUID id;
    private Integer weight;
    private LocalDateTime date;
    private String description;
    private String name;
}
