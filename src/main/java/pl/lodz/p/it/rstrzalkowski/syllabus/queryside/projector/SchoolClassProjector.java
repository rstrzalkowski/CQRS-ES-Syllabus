package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SchoolClassRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentAssignedToClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentUnassignedFromClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SchoolClassProjector {
    private final SchoolClassRepository schoolClassRepository;
    private final UserRepository userRepository;
    private final RealisationRepository realisationRepository;
    private final GradeRepository gradeRepository;

    @EventHandler
    public void on(SchoolClassCreatedEvent event) {
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
    public void on(StudentAssignedToClassEvent event) {
        UserEntity student = userRepository.findById(event.getStudentId()).orElseThrow();
        SchoolClassEntity schoolClass = schoolClassRepository.findById(event.getSchoolClassId()).orElseThrow();

        student.setSchoolClass(schoolClass);
        schoolClass.getStudents().add(student);

        userRepository.save(student);
        schoolClassRepository.save(schoolClass);
    }

    @EventHandler
    public void on(StudentUnassignedFromClassEvent event) {
        UserEntity student = userRepository.findById(event.getStudentId()).orElseThrow();
        SchoolClassEntity schoolClass = schoolClassRepository.findById(event.getSchoolClassId()).orElseThrow();

        student.setSchoolClass(null);
        schoolClass.getStudents().remove(student);

        userRepository.save(student);
        schoolClassRepository.save(schoolClass);
    }

    @EventHandler
    public void on(SchoolClassArchivedEvent event) {
        SchoolClassEntity schoolClass = schoolClassRepository.findById(event.getId())
            .orElseThrow();

        schoolClass.setSupervisingTeacher(null);
        schoolClass.getStudents().forEach((student) -> student.setSchoolClass(null));

        List<RealisationEntity> realisations =
            realisationRepository.findAllByArchivedAndSchoolClassId(false, schoolClass.getId());
        realisations.forEach((realisation -> {
            realisation.getActivities().forEach(activity -> {
                gradeRepository.findByActivityIdAndArchived(activity.getId(), false)
                    .forEach(grade -> grade.setArchived(true));

                activity.setArchived(true);
            });

            realisation.getPosts()
                .forEach(post -> post.setArchived(true));

            realisation.setArchived(true);
            realisationRepository.save(realisation);
        }));

        schoolClass.setArchived(true);
        schoolClassRepository.save(schoolClass);
    }
}
