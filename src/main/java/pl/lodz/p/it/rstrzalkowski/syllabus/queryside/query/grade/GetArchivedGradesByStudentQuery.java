package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade;


import org.springframework.data.domain.Pageable;

public record GetArchivedGradesByStudentQuery(Long studentId, Pageable pageable) {
}
