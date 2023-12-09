package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.SubjectQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetActiveSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetArchivedSubjectsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetSubjectByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject.GetSubjectsByNameContainingQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
@ReadApplicationBean
public class SubjectQueryController {

    private final SubjectQueryHandler subjectQueryHandler;

    @GetMapping("/{id}")
    @Secured({"DIRECTOR", "OFFICE", "ADMIN"})
    public SubjectDTO getSubjectById(@PathVariable("id") UUID id) {
        return subjectQueryHandler.handle(new GetSubjectByIdQuery(id));
    }

    @GetMapping("/search")
    @Secured({"DIRECTOR", "OFFICE", "ADMIN"})
    public Page<SubjectDTO> getSubjectByName(@Param("name") String name, Pageable pageable) {
        return subjectQueryHandler.handle(new GetSubjectsByNameContainingQuery(name, pageable));
    }

    @GetMapping
    @Secured({"DIRECTOR", "OFFICE", "ADMIN"})
    public Page<SubjectDTO> getActiveSubjects(Pageable pageable) {
        return subjectQueryHandler.handle(new GetActiveSubjectsQuery(pageable));
    }

    @GetMapping("/archived")
    @Secured({"DIRECTOR", "OFFICE", "ADMIN"})
    public Page<SubjectDTO> getArchivedSubjects(Pageable pageable) {
        return subjectQueryHandler.handle(new GetArchivedSubjectsQuery(pageable));
    }
}
