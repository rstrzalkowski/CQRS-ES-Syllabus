package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Data
public class Activity extends AbstractEntity {

    private UUID teacherId;

    private String name;

    private LocalDateTime date;

    private Integer weight;

    private String description;

    private List<Grade> grades;

    private boolean edited;

    public Activity(UUID teacherId, String name, LocalDateTime date, Integer weight, String description,
                    boolean edited) {
        this.teacherId = teacherId;
        this.name = name;
        this.date = date;
        this.weight = weight;
        this.description = description;
        this.edited = edited;
    }
}
