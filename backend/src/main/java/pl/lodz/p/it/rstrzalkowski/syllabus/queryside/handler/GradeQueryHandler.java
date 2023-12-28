package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.GradeDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.GradeEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.grade.GradeNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.user.UserNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetActiveGradesByStudentQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetArchivedGradesByStudentQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetGradeByActivityAndStudentQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetGradeByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetOwnGradesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.ActivityRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class GradeQueryHandler {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @QueryHandler
    public Page<GradeDTO> handle(GetActiveGradesByStudentQuery query) {
        return gradeRepository.findAllByArchivedAndStudentId(false, query.studentId(), query.pageable())
            .map(gradeEntity -> new GradeDTO(gradeEntity.getActivity(), gradeEntity));
    }

    @QueryHandler
    public Page<GradeDTO> handle(GetArchivedGradesByStudentQuery query) {
        return gradeRepository.findAllByArchivedAndStudentId(true, query.studentId(), query.pageable())
            .map(gradeEntity -> new GradeDTO(gradeEntity.getActivity(), gradeEntity));
    }

    @QueryHandler
    public GradeDTO handle(GetGradeByActivityAndStudentQuery query) {
        return new GradeDTO(gradeRepository.findByActivityIdAndStudentId(query.activityId(), query.studentId())
            .orElseThrow(GradeNotFoundException::new));
    }

    @QueryHandler
    public GradeDTO handle(GetGradeByIdQuery query) {
        return new GradeDTO(gradeRepository.findById(query.id())
            .orElseThrow(GradeNotFoundException::new));
    }

    @QueryHandler
    public Page<GradeDTO> handle(GetOwnGradesByRealisationQuery query) {
        UserEntity user = userRepository.findById(query.userId()).orElseThrow(UserNotFoundException::new);

        Page<ActivityEntity> activities = activityRepository
            .findByRealisationIdAndArchived(query.realisationId(), false, query.pageable());
        List<GradeEntity> grades = gradeRepository
            .findAllByArchivedAndStudentIdAndActivityRealisationId(false, user.getId(), query.realisationId());

        return activities.map((activity -> {
            AtomicReference<GradeEntity> gradeRef = new AtomicReference<>();
            grades.stream()
                .anyMatch((grade) -> {
                    if (Objects.equals(grade.getActivity().getId(), activity.getId())) {
                        gradeRef.set(grade);
                        return true;
                    }
                    return false;
                });
            return new GradeDTO(activity, gradeRef.get());
        }));
    }
}
