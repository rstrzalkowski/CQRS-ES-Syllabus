package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostEntity extends AbstractEntity {
    @JsonIgnore
    @ManyToOne
    private RealisationEntity realisation;

    @ManyToOne
    private UserEntity teacher;

    @Column(length = 40)
    private String title;

    @Column(length = 2000)
    private String content;

    private boolean edited;

    private boolean archived;
}
