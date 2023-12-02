package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.grades.entity;

import lombok.AllArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.AbstractEntity;

import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
public class Grade extends AbstractEntity {

    private UUID teacherId;

    private UUID studentId;

    private Integer value;

    private LocalDateTime date;

    private String comment;

    public Grade(UUID id, UUID teacherId, UUID studentId, Integer value, LocalDateTime date, String comment) {
        setId(id);
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.value = value;
        this.date = date;
        this.comment = comment;
    }
}
