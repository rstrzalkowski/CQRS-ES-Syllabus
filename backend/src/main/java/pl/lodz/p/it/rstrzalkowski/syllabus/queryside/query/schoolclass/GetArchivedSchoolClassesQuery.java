package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.schoolclass;


import org.springframework.data.domain.Pageable;

public record GetArchivedSchoolClassesQuery(Pageable pageable) {
}
