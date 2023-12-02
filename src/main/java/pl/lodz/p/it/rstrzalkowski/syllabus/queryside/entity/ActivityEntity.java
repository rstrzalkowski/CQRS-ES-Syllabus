package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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
