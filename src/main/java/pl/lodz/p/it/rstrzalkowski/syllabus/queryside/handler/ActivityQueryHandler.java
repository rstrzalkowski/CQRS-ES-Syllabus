package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.ActivityDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.GradeOfActivityDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.activity.ActivityNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetActiveActivitiesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetActivityByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetArchivedActivitiesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetIncomingActivitiesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetIncomingActivitiesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetGradesOfActivityQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.ActivityRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class ActivityQueryHandler {

    private final ActivityRepository activityRepository;

    @QueryHandler
    public Page<ActivityDTO> handle(GetActiveActivitiesByRealisationQuery query) {
        Page<ActivityEntity> activities =
            activityRepository.findByRealisationIdAndArchived(query.realisationId(), false, query.pageable());

        return activities.map((ActivityDTO::new));
    }

    @QueryHandler
    public Page<ActivityDTO> handle(GetIncomingActivitiesByRealisationQuery query) {
        Page<ActivityEntity> activities =
            activityRepository.findByRealisationIdAndArchivedAndDateAfter(query.realisationId(), false,
                LocalDateTime.now(),
                query.pageable());
        return activities.map((ActivityDTO::new));
    }


    @QueryHandler
    public Page<ActivityEntity> handle(GetArchivedActivitiesByRealisationQuery query) {
        return activityRepository.findByRealisationIdAndArchived(query.realisationId(), true, query.pageable());
    }


    @QueryHandler
    public ActivityEntity handle(GetActivityByIdQuery query) {
        return activityRepository.findById(query.id())
            .orElseThrow(ActivityNotFoundException::new);
    }

    @QueryHandler
    public List<GradeOfActivityDTO> handle(GetGradesOfActivityQuery query) {
        ActivityEntity activity = handle(new GetActivityByIdQuery(query.activityId()));
        SchoolClassEntity schoolClass = activity.getRealisation().getSchoolClass();
        return schoolClass
            .getStudents().stream()
            .sorted(Comparator.comparing(UserEntity::getLastName))
            .map((student) -> new GradeOfActivityDTO(student, query.activityId()))
            .collect(Collectors.toList());
    }

    @QueryHandler
    public Page<ActivityDTO> handle(GetIncomingActivitiesQuery query) {
        throw new RuntimeException("Not implemented yet");
//        return activityRepository.findByRealisation_SchoolClass_Students_IdAndArchivedAndDateGreaterThanEqual(query, false,
//                LocalDateTime.now(), pageable)
//            .map(ActivityDTO::new);
    }
}
