package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record GetIncomingActivitiesByRealisationQuery(UUID realisationId, Pageable pageable) {
}
