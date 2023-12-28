package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;

import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@Data
public class Grade extends AbstractEntity {

    private UUID teacherId;

    private UUID studentId;

    private Integer value;

    private LocalDateTime date;

    private String comment;
}
