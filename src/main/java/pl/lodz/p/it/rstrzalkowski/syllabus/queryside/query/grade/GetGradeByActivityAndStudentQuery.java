package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade;


import java.util.UUID;

public record GetGradeByActivityAndStudentQuery(UUID activityId, UUID studentId) {

}
