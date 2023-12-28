package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.GradeEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SubjectProjector {
    private final SubjectRepository subjectRepository;
    private final RealisationRepository realisationRepository;
    private final GradeRepository gradeRepository;

    @EventHandler
    public void on(SubjectCreatedEvent event) {
        SubjectEntity subject = new SubjectEntity(
            event.getId(),
            event.getName(),
            event.getAbbreviation());
        subject.setCreatedAt(event.getCreatedAt());

        subjectRepository.save(subject);
    }

    @EventHandler
    public void on(SubjectUpdatedEvent event) {
        SubjectEntity subjectEntity = subjectRepository.findById(event.getId()).orElseThrow();

        subjectEntity.setName(event.getName());
        subjectEntity.setAbbreviation(event.getAbbreviation());
        subjectEntity.setImageUrl(event.getAbbreviation());

        subjectRepository.save(subjectEntity);
    }

    @EventHandler
    public void on(SubjectArchivedEvent event) {
        SubjectEntity subject = subjectRepository.findById(event.getId()).orElseThrow();

        List<RealisationEntity> realisations = realisationRepository
            .findByArchivedAndSubjectId(false, subject.getId());

        realisations.forEach(realisation -> {
            realisation.getPosts().forEach(post -> post.setArchived(true));
            realisation.getActivities().forEach(activity -> {
                List<GradeEntity> grades = gradeRepository.findByActivityIdAndArchived(activity.getId(), false);
                grades.forEach((grade -> grade.setArchived(true)));
                activity.setArchived(true);
            });
            realisation.setArchived(true);
        });

        subject.setArchived(true);
        subjectRepository.save(subject);
    }
}
