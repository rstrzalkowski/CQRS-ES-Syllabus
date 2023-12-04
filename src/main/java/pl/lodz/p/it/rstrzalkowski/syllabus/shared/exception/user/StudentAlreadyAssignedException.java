package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user;

import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ResponseConflictException;

public class StudentAlreadyAssignedException extends ResponseConflictException {
    public StudentAlreadyAssignedException() {
        super();
    }
}
