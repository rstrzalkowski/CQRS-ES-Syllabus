package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SchoolClassEntity extends AbstractEntity {
    private Integer level;

    private String name;

    private String fullName;

    @ManyToOne
    private UserEntity supervisingTeacher;

    private boolean archived;

    @OneToMany(mappedBy = "schoolClass")
    private Set<UserEntity> students = new LinkedHashSet<>();

    public SchoolClassEntity(UUID id, Integer level, UserEntity supervisingTeacher, String name, String fullName) {
        super(id);
        this.level = level;
        this.supervisingTeacher = supervisingTeacher;
        this.name = name;
        this.fullName = fullName;
    }

    @JsonIgnore
    public String getSchoolClassName() {
        return level + " " + name;
    }
}
