package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.AverageGradeDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.RealisationDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.GradeEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.realisation.RealisationNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetActiveRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetArchivedRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetOwnRealisationsQueryAsStudent;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetOwnRealisationsQueryAsTeacher;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetRealisationAverageGradeQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetRealisationInfoByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.GradeRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class RealisationQueryHandler {

    private final RealisationRepository realisationRepository;
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    @QueryHandler
    public Page<RealisationDTO> handle(GetActiveRealisationsQuery query) {
        return realisationRepository.findAllByArchived(false, query.pageable()).map(RealisationDTO::new);
    }

    @QueryHandler
    public Page<RealisationDTO> handle(GetArchivedRealisationsQuery query) {
        return realisationRepository.findAllByArchived(true, query.pageable()).map(RealisationDTO::new);
    }

    @QueryHandler
    public AverageGradeDTO handle(GetRealisationAverageGradeQuery query) {
        List<GradeEntity> grades =
                gradeRepository.findAllByArchivedAndActivityArchivedAndActivityRealisationIdAndStudentId(false, false,
                        query.id(),
                        query.studentId());

        double sum = 0;
        double weights = 0;
        for (GradeEntity grade : grades) {
            sum += (grade.getValue() * grade.getActivity().getWeight());
            weights += grade.getActivity().getWeight();
        }
        if (grades.size() == 0) {
            return new AverageGradeDTO(0.0);
        }
        return new AverageGradeDTO(sum / weights);
    }

    @QueryHandler
    public List<SubjectDTO> handle(GetOwnRealisationsQueryAsStudent query) {
        UserEntity user = userRepository.findById(query.getStudentId()).orElseThrow();
        UUID schoolClassId = user.getSchoolClass().getId();
        if (schoolClassId == null) {
            return List.of();
        }
        List<RealisationEntity> realisations = realisationRepository.findAllByArchivedAndSchoolClassId(false, schoolClassId);

        return realisations.stream()
                .map(SubjectDTO::new)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public List<SubjectDTO> handle(GetOwnRealisationsQueryAsTeacher query) {
        UserEntity user = userRepository.findById(query.studentId()).orElseThrow();
        List<RealisationEntity> realisations = realisationRepository.findAllByArchivedAndTeacherId(false, user.getId());

        return realisations.stream()
                .map(SubjectDTO::new)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public RealisationDTO handle(GetRealisationInfoByIdQuery query) {
        RealisationEntity realisation = realisationRepository.findById(query.id())
                .orElseThrow(RealisationNotFoundException::new);

        return new RealisationDTO(realisation);
    }
}
