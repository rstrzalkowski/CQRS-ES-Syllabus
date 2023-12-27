package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class UserProjector {
    private final UserRepository userRepository;

    @EventHandler
    public void createUser(UserCreatedEvent event) {
        UserEntity user = new UserEntity(
            event.getId(),
            event.getEmail(),
            event.getFirstName(),
            event.getLastName(),
            event.getPersonalId(),
            event.getDescription(),
            event.getImageUrl());
        user.setCreatedAt(event.getCreatedAt());

        userRepository.save(user);
    }
}
