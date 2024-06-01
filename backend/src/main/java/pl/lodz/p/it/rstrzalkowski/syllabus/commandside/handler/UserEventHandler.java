package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserPersonalIdEmailRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserCreationFailedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Component
@RequiredArgsConstructor
@ProcessingGroup("user")
@WriteApplicationBean
public class UserEventHandler {

    private final UserPersonalIdEmailRepository userPersonalIdEmailRepository;

    @EventHandler
    public void on(UserCreatedEvent event) {
        userPersonalIdEmailRepository.findByPersonalId(event.getPersonalId()).ifPresent((userPersonalIdEmailEntity -> {
            userPersonalIdEmailEntity.setAggregateId(event.getId());
        }));
    }

    @EventHandler
    public void on(UserCreationFailedEvent event) {
        userPersonalIdEmailRepository.findByPersonalId(event.getPersonalId()).ifPresent((userPersonalIdEmailRepository::delete));
    }
}
