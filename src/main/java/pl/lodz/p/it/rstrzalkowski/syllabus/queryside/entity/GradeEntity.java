package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

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
