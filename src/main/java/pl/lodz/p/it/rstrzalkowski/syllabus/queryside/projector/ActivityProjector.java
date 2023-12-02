package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.ActivityRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class ActivityProjector {
    private final RealisationRepository realisationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @EventHandler
    public void createActivity(ActivityCreatedEvent event) {
        UserEntity teacher = userRepository.findById(event.getTeacherId()).orElse(null);
        RealisationEntity realisation = realisationRepository.findById(event.getRealisationId()).orElse(null);

        ActivityEntity activity = new ActivityEntity(
            event.getId(),
            realisation,
            teacher,
            event.getName(),
            event.getDate(),
            event.getWeight(),
            event.getDescription()
        );

        activityRepository.save(activity);
    }
}
