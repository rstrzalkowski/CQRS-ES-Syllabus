package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query;

import org.springframework.data.domain.Pageable;

public record GetAllSubjectsQuery(Pageable pageable) {
}
