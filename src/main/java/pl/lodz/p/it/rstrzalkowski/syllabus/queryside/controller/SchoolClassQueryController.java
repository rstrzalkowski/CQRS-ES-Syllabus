package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SchoolClassDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.SchoolClassQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.schoolclass.GetActiveSchoolClassesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.schoolclass.GetArchivedSchoolClassesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.schoolclass.GetSchoolClassByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
@ReadApplicationBean
public class SchoolClassQueryController {

    private final SchoolClassQueryHandler schoolClassQueryHandler;

    @GetMapping("/{id}")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public SchoolClassDTO getSchoolClassById(@PathVariable("id") UUID id) {
        return schoolClassQueryHandler.handle(new GetSchoolClassByIdQuery(id));
    }

    @GetMapping
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<SchoolClassDTO> getActiveSchoolClasses(Pageable pageable) {
        return schoolClassQueryHandler.handle(new GetActiveSchoolClassesQuery(pageable));
    }

    @GetMapping("/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<SchoolClassDTO> getArchivedSchoolClasses(Pageable pageable) {
        return schoolClassQueryHandler.handle(new GetArchivedSchoolClassesQuery(pageable));
    }
}
