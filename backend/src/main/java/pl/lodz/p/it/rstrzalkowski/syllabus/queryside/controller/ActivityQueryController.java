package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.ActivityDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.GradeOfActivityDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.ActivityQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetActivityByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetIncomingActivitiesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetGradesOfActivityQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
@ReadApplicationBean
public class ActivityQueryController {

    private final ActivityQueryHandler activityQueryHandler;

    @GetMapping("/{id}")
    @Secured({"TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public ActivityEntity getActivityById(@PathVariable("id") UUID id) {
        return activityQueryHandler.handle(new GetActivityByIdQuery(id));
    }

    @GetMapping("/{id}/grades")
    @Secured({"TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public List<GradeOfActivityDTO> getGradesOfActivity(@PathVariable("id") UUID id, Pageable pageable) {
        return activityQueryHandler.handle(new GetGradesOfActivityQuery(id, pageable));
    }

    @GetMapping("/incoming")
    @Secured({"STUDENT"})
    public Page<ActivityDTO> getIncomingActivities(Pageable pageable) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return activityQueryHandler.handle(new GetIncomingActivitiesQuery(userInfo.getId(), pageable));
    }
}
