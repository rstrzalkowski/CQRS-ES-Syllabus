package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SchoolClassDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.schoolclass.SchoolClassNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.schoolclass.GetActiveSchoolClassesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.schoolclass.GetArchivedSchoolClassesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.schoolclass.GetSchoolClassByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SchoolClassRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class SchoolClassQueryHandler {

    private final SchoolClassRepository schoolClassRepository;


    @QueryHandler
    public Page<SchoolClassDTO> handle(GetActiveSchoolClassesQuery query) {
        return schoolClassRepository.findAllByArchived(false, query.pageable()).map(SchoolClassDTO::new);
    }

    @QueryHandler
    public Page<SchoolClassDTO> handle(GetArchivedSchoolClassesQuery query) {
        return schoolClassRepository.findAllByArchived(true, query.pageable()).map(SchoolClassDTO::new);
    }

    @QueryHandler
    public SchoolClassDTO handle(GetSchoolClassByIdQuery query) {
        return new SchoolClassDTO(schoolClassRepository.findById(query.id())
            .orElseThrow(SchoolClassNotFoundException::new));
    }
}
