package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity;

import lombok.AllArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;

import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
public class Activity extends AbstractEntity {

    private UUID teacherId;

    private String name;

    private LocalDateTime date;

    private Integer weight;

    private String description;

    private boolean edited;
}
