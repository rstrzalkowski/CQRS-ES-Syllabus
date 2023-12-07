package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SubjectProjector {
    private final SubjectRepository subjectRepository;

    @EventHandler
    public void on(SubjectCreatedEvent event) {
        SubjectEntity subjectEntity = new SubjectEntity(event.getId(), event.getName(), event.getAbbreviation());
        subjectRepository.save(subjectEntity);
    }

    @EventHandler
    public void on(SubjectUpdatedEvent event) {
        SubjectEntity subjectEntity = subjectRepository.findById(event.getId()).orElseThrow();

        subjectEntity.setName(event.getName());
        subjectEntity.setAbbreviation(event.getAbbreviation());

        subjectRepository.save(subjectEntity);
    }
}
