package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
