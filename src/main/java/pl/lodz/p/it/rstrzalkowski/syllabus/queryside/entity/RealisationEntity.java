package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RealisationEntity extends AbstractEntity {
    @ManyToOne
    private SubjectEntity subject;

    @ManyToOne
    private SchoolClassEntity schoolClass;

    @ManyToOne
    private UserEntity teacher;

    @Column(name = "realisation_year")
    private Year year;

    private boolean archived;

    private boolean edited;

    @OneToMany(mappedBy = "realisation")
    private Set<PostEntity> posts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "realisation")
    private Set<ActivityEntity> activities = new LinkedHashSet<>();

    public RealisationEntity(UUID id, SubjectEntity subject, SchoolClassEntity schoolClass, UserEntity teacher, Year year) {
        super(id);
        this.subject = subject;
        this.schoolClass = schoolClass;
        this.teacher = teacher;
        this.year = year;
    }
}
