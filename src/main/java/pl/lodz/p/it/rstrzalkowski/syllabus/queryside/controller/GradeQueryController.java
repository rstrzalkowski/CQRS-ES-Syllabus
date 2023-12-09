package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.GradeDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.GradeQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade.GetGradeByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.NotImplementedException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
@ReadApplicationBean
public class GradeQueryController {

    private final GradeQueryHandler gradeQueryHandler;
    private final AccessGuard accessGuard;

    @GetMapping("/{id}")
    @Secured({"TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public GradeDTO getGradeById(@PathVariable("id") UUID id) {
        accessGuard.checkAccessToGrade(id);
        return gradeQueryHandler.handle(new GetGradeByIdQuery(id));
    }

    @GetMapping("/own")
    @Secured({"STUDENT"})
    public Page<GradeDTO> getRecentGrades(Pageable pageable) {
        throw new NotImplementedException();
        //return gradeQueryHandler.handle(new GetActiveGradesByStudentQuery(pageable));
    }
}
