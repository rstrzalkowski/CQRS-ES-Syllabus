package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectNameUpdateFailedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Component
@RequiredArgsConstructor
@ProcessingGroup("subject")
@WriteApplicationBean
@Transactional
public class SubjectEventHandler {

    private final SubjectNameRepository subjectNameRepository;

    @EventHandler
    public void on(SubjectCreatedEvent event) {
        subjectNameRepository.findBySubjectName(event.getName()).ifPresent((subjectNameEntity -> {
            subjectNameEntity.setAggregateId(event.getId());
        }));
    }

    @EventHandler
    public void on(SubjectUpdatedEvent event) {
        subjectNameRepository.findByAggregateId(event.getId()).ifPresent((subjectNameEntity -> {
            if (!subjectNameEntity.getSubjectName().equals(event.getName())) {
                subjectNameRepository.delete(subjectNameEntity);
                subjectNameRepository.findBySubjectName(event.getName()).ifPresent((newSubjectNameEntity -> {
                    newSubjectNameEntity.setAggregateId(event.getId());
                }));
            }
        }));
    }

    @EventHandler
    public void on(SubjectNameUpdateFailedEvent event) {
        subjectNameRepository.findBySubjectName(event.getName()).ifPresent(subjectNameRepository::delete);
    }

    @EventHandler
    public void on(SubjectArchivedEvent event) {
        subjectNameRepository.findByAggregateId(event.getId()).ifPresent(subjectNameRepository::delete);
    }
}
