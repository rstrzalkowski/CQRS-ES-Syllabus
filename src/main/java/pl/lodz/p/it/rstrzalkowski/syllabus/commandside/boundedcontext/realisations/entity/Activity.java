package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity;

import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;

import java.time.LocalDateTime;
import java.util.UUID;


public class Activity extends AbstractEntity {

    private UUID teacherId;

    private String name;

    private LocalDateTime date;

    private Integer weight;

    private String description;

    private boolean edited;
}
