package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectEntity extends AbstractEntity {
    private String name;

    private String abbreviation;

    private boolean archived;

    @Transient
    private Integer activeRealisationsCount;

    public SubjectEntity(UUID id, String name, String abbreviation) {
        super(id);
        this.name = name;
        this.abbreviation = abbreviation;
    }
}
