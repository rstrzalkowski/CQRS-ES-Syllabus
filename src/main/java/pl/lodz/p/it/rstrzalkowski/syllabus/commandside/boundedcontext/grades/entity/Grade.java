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

    private Integer weight;

    private String comment;

    private boolean edited;
}
