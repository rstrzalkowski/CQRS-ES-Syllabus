package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.subject.SubjectNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetActiveSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetArchivedSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetSubjectByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetSubjectsByNameContainingQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SubjectQueryHandler {

    private final SubjectRepository subjectRepository;
    private final RealisationRepository realisationRepository;

    @QueryHandler
    public Page<SubjectDTO> handle(GetActiveSubjectsQuery query) {
        Page<SubjectEntity> subjects = subjectRepository.findAllByArchived(false, query.pageable());
        subjects.forEach((subject -> {
            subject.setActiveRealisationsCount(
                realisationRepository.countByArchivedAndSubjectId(false, subject.getId()));
        }));
        return subjects.map(SubjectDTO::new);
    }

    @QueryHandler
    public Page<SubjectDTO> handle(GetArchivedSubjectsQuery query) {
        return subjectRepository.findAllByArchived(true, query.pageable()).map(SubjectDTO::new);
    }

    @QueryHandler
    public Page<SubjectDTO> handle(GetSubjectsByNameContainingQuery query) {
        return subjectRepository.findSubjectByNameContainingIgnoreCase(query.name(), query.pageable())
            .map(SubjectDTO::new);
    }

    @QueryHandler
    public SubjectDTO handle(GetSubjectByIdQuery query) {
        return new SubjectDTO(subjectRepository.findById(query.id())
            .orElseThrow(SubjectNotFoundException::new));
    }
}
