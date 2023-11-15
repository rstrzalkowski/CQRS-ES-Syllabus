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
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.GetAllSubjectsQuery;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectQueryController {

    private final SubjectQueryHandler subjectQueryHandler;
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<SubjectDto> getAllSubjects(Pageable pageable) {
        return subjectQueryHandler.handle(new GetAllSubjectsQuery(pageable));
    }
}
