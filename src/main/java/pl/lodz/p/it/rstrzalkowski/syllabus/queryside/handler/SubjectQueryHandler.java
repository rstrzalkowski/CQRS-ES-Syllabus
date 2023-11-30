package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetActiveSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetArchivedSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.converter.SubjectConverter;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SubjectQueryHandler {

    private final SubjectRepository subjectRepository;

    @QueryHandler
    public Page<SubjectDto> handle(GetActiveSubjectsQuery query) {
        return subjectRepository.findAllByArchived(false, query.pageable())
                .map(SubjectConverter::convertToSubjectDto);
    }

    @QueryHandler
    public Page<SubjectDto> handle(GetArchivedSubjectsQuery query) {
        return subjectRepository.findAllByArchived(true, query.pageable())
                .map(SubjectConverter::convertToSubjectDto);
    }
}
