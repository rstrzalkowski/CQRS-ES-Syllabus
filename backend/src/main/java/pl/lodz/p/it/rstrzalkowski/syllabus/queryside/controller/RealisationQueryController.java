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
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.AverageGradeDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.GradeDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.PostDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.RealisationDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.ActivityQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.GradeQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.PostQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.RealisationQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetActiveActivitiesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity.GetIncomingActivitiesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetOwnGradesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post.GetActivePostsByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetActiveRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetArchivedRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetOwnRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetRealisationAverageGradeQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetRealisationByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetRealisationInfoByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/realisations")
@ReadApplicationBean
public class RealisationQueryController {

    private final RealisationQueryHandler realisationQueryHandler;
    private final PostQueryHandler postQueryHandler;
    private final ActivityQueryHandler activityQueryHandler;
    private final GradeQueryHandler gradeQueryHandler;
    private final AccessGuard accessGuard;

    @GetMapping("/{id}/secured")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public RealisationEntity getRealisationById(@PathVariable("id") UUID id) {
        return realisationQueryHandler.handle(new GetRealisationByIdQuery(id));
    }

    @GetMapping("/{id}")
    @Secured({"STUDENT", "TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public RealisationDTO getRealisationInfoById(@PathVariable("id") UUID id) {
        accessGuard.checkAccessToRealisation(id);
        return realisationQueryHandler.handle(new GetRealisationInfoByIdQuery(id));
    }

    @GetMapping
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<RealisationDTO> getActiveRealisations(Pageable pageable) {
        return realisationQueryHandler.handle(new GetActiveRealisationsQuery(pageable));
    }

    @GetMapping("/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<RealisationDTO> getArchivedRealisations(Pageable pageable) {
        return realisationQueryHandler.handle(new GetArchivedRealisationsQuery(pageable));
    }

    @GetMapping("/{id}/posts")
    @Secured({"STUDENT", "TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public Page<PostDTO> getActivePostsOfRealisation(@PathVariable("id") UUID id, Pageable pageable) {
        accessGuard.checkAccessToRealisation(id);
        return postQueryHandler.handle(new GetActivePostsByRealisationQuery(id, pageable));
    }

    @GetMapping("/{id}/grades")
    @Secured("STUDENT")
    public Page<GradeDTO> getOwnGradesOfRealisation(@PathVariable("id") UUID id, Pageable pageable) {
        accessGuard.checkAccessToRealisation(id);
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return gradeQueryHandler.handle(new GetOwnGradesByRealisationQuery(id, userInfo.getId(), pageable));
    }

    @GetMapping("/{id}/activities")
    @Secured({"STUDENT", "TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public Page<ActivityDTO> getActiveActivitiesOfRealisation(@PathVariable("id") UUID id, Pageable pageable) {
        accessGuard.checkAccessToRealisation(id);
        return activityQueryHandler.handle(new GetActiveActivitiesByRealisationQuery(id, pageable));
    }

    @GetMapping("/{id}/activities/incoming")
    @Secured({"STUDENT", "TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public Page<ActivityDTO> getIncomingActivitiesOfRealisation(@PathVariable("id") UUID id, Pageable pageable) {
        accessGuard.checkAccessToRealisation(id);
        return activityQueryHandler.handle(new GetIncomingActivitiesByRealisationQuery(id, pageable));
    }

    @GetMapping("/{id}/average")
    @Secured("STUDENT")
    public AverageGradeDTO getRealisationAverageGrade(@PathVariable("id") UUID id) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accessGuard.checkAccessToRealisation(id);
        return realisationQueryHandler.handle(new GetRealisationAverageGradeQuery(id, userInfo.getId()));
    }

    @GetMapping("/me")
    @Secured({"STUDENT", "TEACHER"})
    public List<SubjectDTO> getOwnRealisations() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return realisationQueryHandler.handle(new GetOwnRealisationsQuery(userInfo.getId()));
    }
}
