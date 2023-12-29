package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Year;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RealisationCreatedEvent {
    private UUID id;
    private UUID subjectId;
    private UUID schoolClassId;
    private UUID teacherId;
    private Year year;
    private Timestamp createdAt;
}