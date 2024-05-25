package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectNotFoundCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@ProcessingGroup("subject")
@WriteApplicationBean
public class SubjectEventHandler {

    private final SubjectNameRepository subjectNameRepository;
    private final CommandGateway commandGateway;

    @EventHandler
    public void on(SubjectCreatedEvent event) {
        subjectNameRepository.save(new SubjectNameEntity(event.getId(), event.getName()));
    }

    @EventHandler
    public void on(SubjectUpdatedEvent event) {
        SubjectNameEntity subjectNameEntity = subjectNameRepository.findById(event.getId())
            .orElseThrow(SubjectNotFoundCommandExecutionException::new);

        if (Objects.equals(subjectNameEntity.getSubjectName(), event.getName())) {
            subjectNameEntity.setSubjectName(event.getName());
            subjectNameRepository.save(subjectNameEntity);
        }
    }

    @EventHandler
    public void on(SubjectArchivedEvent event) {
        SubjectNameEntity subjectNameEntity = subjectNameRepository.findById(event.getId())
            .orElseThrow(SubjectNotFoundCommandExecutionException::new);

        subjectNameRepository.delete(subjectNameEntity);
    }
}
