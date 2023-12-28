package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActivityEntity extends AbstractEntity {
    @JsonIgnore
    @ManyToOne
    private RealisationEntity realisation;

    @ManyToOne
    private UserEntity teacher;

    private String name;

    private LocalDateTime date;

    private Integer weight;

    private String description;

    private boolean edited;

    private boolean archived;

    public ActivityEntity(UUID id, RealisationEntity realisation, UserEntity teacher, String name,
                          LocalDateTime date, Integer weight, String description) {
        super(id);
        this.realisation = realisation;
        this.teacher = teacher;
        this.name = name;
        this.date = date;
        this.weight = weight;
        this.description = description;
    }
}
