package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SchoolClassRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class RealisationProjector {
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserRepository userRepository;
    private final RealisationRepository realisationRepository;

    @EventHandler
    public void createRealisation(RealisationCreatedEvent event) {
        SchoolClassEntity schoolClass = schoolClassRepository.findById(event.getSchoolClassId()).orElse(null);
        SubjectEntity subject = subjectRepository.findById(event.getSubjectId()).orElse(null);
        UserEntity teacher = userRepository.findById(event.getTeacherId()).orElse(null);

        RealisationEntity realisationEntity = new RealisationEntity(event.getId(), subject, schoolClass, teacher, event.getYear());
        realisationRepository.save(realisationEntity);
    }
}
