package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.GradeDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.grade.GradeNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetActiveGradesByStudentQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetArchivedGradesByStudentQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetGradeByActivityAndStudentQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetGradeByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetOwnGradesByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.NotImplementedException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class GradeQueryHandler {

    private final GradeRepository gradeRepository;

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
        throw new NotImplementedException();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        Page<Activity> activities = activityRepository
//            .findByRealisationIdAndArchived(realisationId, false, pageable);
//        List<Grade> grades = gradeRepository
//            .findAllByArchivedAndStudentIdAndActivityRealisationId(false, user.getId(), realisationId);
//
//        return activities.map((activity -> {
//            AtomicReference<Grade> gradeRef = new AtomicReference<>();
//            grades.stream()
//                .anyMatch((grade) -> {
//                    if (Objects.equals(grade.getActivity().getId(), activity.getId())) {
//                        gradeRef.set(grade);
//                        return true;
//                    }
//                    return false;
//                });
//            return new GradeDTO(activity, gradeRef.get());
//        }));
    }
}
