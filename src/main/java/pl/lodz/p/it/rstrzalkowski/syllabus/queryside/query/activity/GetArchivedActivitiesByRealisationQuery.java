package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record GetArchivedActivitiesByRealisationQuery(UUID realisationId, Pageable pageable) {
}
