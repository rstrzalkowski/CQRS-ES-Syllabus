package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.LevelEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.LevelRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SchoolClassRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SchoolClassProjector {
    private final SchoolClassRepository schoolClassRepository;
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;

    @EventHandler
    public void createSchoolClass(SchoolClassCreatedEvent event) {
        LevelEntity level = levelRepository.findById(event.getLevelId()).orElseThrow();
        UserEntity teacher = userRepository.findById(event.getTeacherId()).orElseThrow();

        SchoolClassEntity schoolClassEntity = new SchoolClassEntity(
                event.getId(),
                level,
                teacher,
                event.getName(),
                event.getFullName());

        schoolClassRepository.save(schoolClassEntity);
    }
}
