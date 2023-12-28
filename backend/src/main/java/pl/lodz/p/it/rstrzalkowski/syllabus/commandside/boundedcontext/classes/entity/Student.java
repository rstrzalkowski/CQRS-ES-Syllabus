package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.classes.entity;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.Objects;
import java.util.UUID;


@NoArgsConstructor
@WriteApplicationBean
public class Student extends AbstractEntity {
    public Student(UUID id) {
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Student that = (Student) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
