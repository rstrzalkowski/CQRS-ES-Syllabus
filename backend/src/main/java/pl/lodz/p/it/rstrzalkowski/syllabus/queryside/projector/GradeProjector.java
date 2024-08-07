package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.GradeEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.ActivityRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class GradeProjector {
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @EventHandler
    public void on(GradeCreatedEvent event) {
        UserEntity teacher = userRepository.findById(event.getTeacherId()).orElse(null);
        UserEntity student = userRepository.findById(event.getStudentId()).orElse(null);
        ActivityEntity activity = activityRepository.findById(event.getActivityId()).orElse(null);

        GradeEntity grade = new GradeEntity(
            event.getId(),
            activity,
            teacher,
            student,
            event.getValue(),
            event.getDate(),
            event.getComment()
        );
        grade.setCreatedAt(event.getCreatedAt());

        gradeRepository.save(grade);
    }

    @EventHandler
    public void on(GradeUpdatedEvent event) {
        GradeEntity grade = gradeRepository.findById(event.getId()).orElseThrow();

        grade.setValue(event.getValue());
        grade.setComment(event.getComment());
        grade.setDate(event.getDate());
        grade.setEdited(true);

        gradeRepository.save(grade);
    }
}
