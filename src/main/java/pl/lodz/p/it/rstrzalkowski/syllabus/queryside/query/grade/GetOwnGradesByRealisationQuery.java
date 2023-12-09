package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.grade;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record GetOwnGradesByRealisationQuery(UUID realisationId, Pageable pageable) {
}
