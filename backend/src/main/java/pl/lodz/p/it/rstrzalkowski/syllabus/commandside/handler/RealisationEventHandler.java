package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisedSubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisedSubjectId;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisedSubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Component
@ProcessingGroup("realisation")
@RequiredArgsConstructor
@WriteApplicationBean
public class RealisationEventHandler {

    private final RealisedSubjectRepository realisedSubjectRepository;

    @EventHandler
    public void on(RealisationCreatedEvent event) {
        realisedSubjectRepository.save(
            new RealisedSubjectEntity(
                new RealisedSubjectId(
                    event.getSubjectId(),
                    event.getSchoolClassId())));
    }
}
