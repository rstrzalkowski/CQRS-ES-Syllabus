package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.GetAllSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.SubjectConverter;

@Service
@RequiredArgsConstructor
public class SubjectQueryHandler {

    private final SubjectRepository subjectRepository;

    @QueryHandler
    public Page<SubjectDto> handle(GetAllSubjectsQuery query) {
        return subjectRepository.findAllByArchived(false, query.pageable())
                .map(SubjectConverter::convertToSubjectDto);
    }
}
