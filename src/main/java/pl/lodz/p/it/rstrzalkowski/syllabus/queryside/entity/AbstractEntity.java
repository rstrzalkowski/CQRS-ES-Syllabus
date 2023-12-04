package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class AbstractEntity {
    @Id
    private UUID id;

    @Version
    private Long version;

    public AbstractEntity(UUID id) {
        this.id = id;
    }
}
