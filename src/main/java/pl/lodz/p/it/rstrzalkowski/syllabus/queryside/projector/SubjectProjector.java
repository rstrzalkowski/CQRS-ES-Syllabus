package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;

@Service
@RequiredArgsConstructor
public class SubjectProjector {
    private final SubjectRepository subjectRepository;

    @EventHandler
    public void createSubject(SubjectCreatedEvent event) {
        SubjectEntity subjectEntity = new SubjectEntity(event.getId(), event.getName(), event.getAbbreviation());
        subjectRepository.save(subjectEntity);
    }
}
