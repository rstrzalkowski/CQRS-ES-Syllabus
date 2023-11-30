package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.handler;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Component
@ProcessingGroup("subject")
@WriteApplicationBean
public class SubjectEventHandler {
    @EventHandler
    public void on(SubjectCreatedEvent event, SubjectNameRepository subjectNameRepository) {
        subjectNameRepository.save(new SubjectNameEntity(event.getName(), event.getId()));
    }
}
