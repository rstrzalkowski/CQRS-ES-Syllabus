package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserPersonalIdEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserPersonalIdRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Component
@RequiredArgsConstructor
@ProcessingGroup("user")
@WriteApplicationBean
public class UserEventHandler {

    private final UserPersonalIdRepository userPersonalIdRepository;

    @EventHandler
    public void on(UserCreatedEvent event) {
        userPersonalIdRepository.save(new UserPersonalIdEntity(event.getEmail(), event.getPersonalId()));
    }
}
