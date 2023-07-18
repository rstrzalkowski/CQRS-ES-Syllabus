package pl.lodz.p.it.rstrzalkowski.syllabus.event;

import java.util.UUID;


public record SubjectCreatedEvent(UUID id, String name, String abbreviation) {

}
