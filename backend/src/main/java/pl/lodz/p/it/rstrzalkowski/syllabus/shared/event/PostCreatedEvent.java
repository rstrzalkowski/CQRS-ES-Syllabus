package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostCreatedEvent {
    private UUID id;
    private UUID realisationId;
    private UUID teacherId;
    private String title;
    private String content;
    private Timestamp createdAt;
}
