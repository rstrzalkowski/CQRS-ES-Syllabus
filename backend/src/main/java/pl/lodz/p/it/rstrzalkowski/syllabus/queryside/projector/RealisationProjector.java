package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SchoolClassRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class RealisationProjector {
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserRepository userRepository;
    private final RealisationRepository realisationRepository;
    private final GradeRepository gradeRepository;

    @EventHandler
    public void on(RealisationCreatedEvent event) {
        SchoolClassEntity schoolClass = schoolClassRepository.findById(event.getSchoolClassId()).orElse(null);
        SubjectEntity subject = subjectRepository.findById(event.getSubjectId()).orElse(null);
        UserEntity teacher = userRepository.findById(event.getTeacherId()).orElse(null);

        RealisationEntity realisation = new RealisationEntity(
            event.getId(),
            subject,
            schoolClass,
            teacher,
            event.getYear());
        realisation.setCreatedAt(event.getCreatedAt());

        realisationRepository.save(realisation);
    }

    @EventHandler
    public void on(RealisationUpdatedEvent event) {
        RealisationEntity realisation = realisationRepository.findById(event.getId()).orElseThrow();

        realisation.setYear(event.getYear());
        realisation.setEdited(true);

        realisationRepository.save(realisation);
    }

    @EventHandler
    public void on(RealisationArchivedEvent event) {
        RealisationEntity realisation = realisationRepository.findById(event.getId()).orElseThrow();

        realisation.getActivities().forEach(activity -> {
            gradeRepository.findByActivityIdAndArchived(activity.getId(), false)
                .forEach(grade -> grade.setArchived(true));

            activity.setArchived(true);
        });

        realisation.getPosts()
            .forEach(post -> post.setArchived(true));

        realisation.setArchived(true);
        realisationRepository.save(realisation);
    }
}
