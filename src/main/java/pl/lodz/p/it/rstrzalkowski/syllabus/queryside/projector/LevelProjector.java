package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.LevelEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.LevelRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.LevelCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class LevelProjector {
    private final LevelRepository levelRepository;

    @EventHandler
    public void createLevel(LevelCreatedEvent event) {
        LevelEntity levelEntity = new LevelEntity(
                event.getId(),
                event.getValue());

        levelRepository.save(levelEntity);
    }
}
