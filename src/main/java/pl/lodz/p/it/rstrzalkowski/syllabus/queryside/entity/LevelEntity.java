package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LevelEntity extends AbstractEntity {
    @Column(name = "level_value")
    private Integer value;

    private boolean archived = false;

    public LevelEntity(UUID id, Integer value) {
        super(id);
        this.value = value;
    }
}
