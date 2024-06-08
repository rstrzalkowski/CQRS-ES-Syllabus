package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.GradeEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.PostEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.activity.ActivityNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.grade.GradeNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.post.PostNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.realisation.NotAffiliatedWithRealisationException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.ActivityRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.PostRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@ReadApplicationBean
public class AccessGuard {

    private final RealisationRepository realisationRepository;
    private final ActivityRepository activityRepository;
    private final PostRepository postRepository;
    private final GradeRepository gradeRepository;

    public void checkAccessToRealisation(UUID realisationId) {

        UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new NotAffiliatedWithRealisationException();
        }
        List<String> privilegedRoles = new ArrayList<>(Arrays.asList("DIRECTOR", "ADMIN"));

        RealisationEntity realisation = realisationRepository.findById(realisationId)
                .orElseThrow(NotAffiliatedWithRealisationException::new);

        if (realisation.isArchived() &&
                user.getRoles().stream().noneMatch(privilegedRoles::contains)) {
            throw new NotAffiliatedWithRealisationException();
        }

        boolean isAffiliatedAsStudent = realisation.getSchoolClass()
                .getStudents()
                .stream()
                .anyMatch((student -> Objects.equals(student.getId(), user.getId())));

        boolean isAffiliatedAsTeacher = Objects.equals(realisation.getTeacher().getId(), user.getId());

        if (!isAffiliatedAsStudent && !isAffiliatedAsTeacher &&
                user.getRoles().stream().noneMatch(privilegedRoles::contains)) {
            throw new NotAffiliatedWithRealisationException();
        }
    }

    public void checkAccessToActivity(UUID activityId) {
        ActivityEntity activity = activityRepository.findById(activityId)
                .orElseThrow(ActivityNotFoundException::new);
        checkAccessToRealisation(activity.getRealisation().getId());
    }

    public void checkAccessToPost(UUID postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        checkAccessToRealisation(post.getRealisation().getId());
    }

    public void checkAccessToGrade(UUID gradeId) {
        GradeEntity grade = gradeRepository.findById(gradeId)
                .orElseThrow(GradeNotFoundException::new);
        checkAccessToRealisation(grade.getActivity().getRealisation().getId());
    }
}
