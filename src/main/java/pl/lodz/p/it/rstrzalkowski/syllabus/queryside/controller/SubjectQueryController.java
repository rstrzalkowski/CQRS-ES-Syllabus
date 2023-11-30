package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.SubjectQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetActiveSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetArchivedSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
@ReadApplicationBean
public class SubjectQueryController {

    private final SubjectQueryHandler subjectQueryHandler;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<SubjectDto> getAllActiveSubjects(Pageable pageable) {
        return subjectQueryHandler.handle(new GetActiveSubjectsQuery(pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/archived")
    public Page<SubjectDto> getAllArchivedSubjects(Pageable pageable) {
        return subjectQueryHandler.handle(new GetArchivedSubjectsQuery(pageable));
    }
}
