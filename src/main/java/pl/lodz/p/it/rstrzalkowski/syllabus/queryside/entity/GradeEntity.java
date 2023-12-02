package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
@Builder
public class GradeEntity extends AbstractEntity {

    @ManyToOne
    private UserEntity teacher;

    @ManyToOne
    private UserEntity student;

    @ManyToOne
    private ActivityEntity activity;

    private Integer value;

    private LocalDateTime date;

    private String comment;

    private boolean archived = false;

    public GradeEntity(UUID id, ActivityEntity activity, UserEntity teacher, UserEntity student, Integer value, LocalDateTime date, String comment) {
        super(id);
        this.teacher = teacher;
        this.student = student;
        this.value = value;
        this.date = date;
        this.comment = comment;
    }
}
