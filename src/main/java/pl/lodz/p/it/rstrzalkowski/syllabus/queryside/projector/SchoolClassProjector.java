package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SchoolClassRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentAssignedToClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentUnassignedFromClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SchoolClassProjector {
    private final SchoolClassRepository schoolClassRepository;
    private final UserRepository userRepository;

    @EventHandler
    public void createSchoolClass(SchoolClassCreatedEvent event) {
        UserEntity teacher = userRepository.findById(event.getTeacherId()).orElseThrow();

        SchoolClassEntity schoolClassEntity = new SchoolClassEntity(
                event.getId(),
                event.getLevel(),
                teacher,
                event.getName(),
                event.getFullName());

        schoolClassRepository.save(schoolClassEntity);
    }

    @EventHandler
    public void assignStudent(StudentAssignedToClassEvent event) {
        UserEntity student = userRepository.findById(event.getStudentId()).orElseThrow();
        SchoolClassEntity schoolClass = schoolClassRepository.findById(event.getSchoolClassId()).orElseThrow();

        student.setSchoolClass(schoolClass);
        schoolClass.getStudents().add(student);

        userRepository.save(student);
        schoolClassRepository.save(schoolClass);
    }

    @EventHandler
    public void unassignStudent(StudentUnassignedFromClassEvent event) {
        UserEntity student = userRepository.findById(event.getStudentId()).orElseThrow();
        SchoolClassEntity schoolClass = schoolClassRepository.findById(event.getSchoolClassId()).orElseThrow();

        student.setSchoolClass(null);
        schoolClass.getStudents().remove(student);

        userRepository.save(student);
        schoolClassRepository.save(schoolClass);
    }
}
