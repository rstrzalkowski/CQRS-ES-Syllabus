package pl.lodz.p.it.rstrzalkowski.syllabus.mock;

import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectUniqueValuesRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectUniqueValuesService;

import java.util.UUID;


public class MockSubjectUniqueValuesService extends SubjectUniqueValuesService {

    public MockSubjectUniqueValuesService(SubjectUniqueValuesRepository subjectUniqueValuesRepository) {
        super(subjectUniqueValuesRepository);
    }

    @Override
    public void lockSubjectName(String name, UUID subjectId) {

    }

    @Override
    public void updateSubjectName(String name, UUID subjectId) {

    }

    @Override
    public void releaseValues(UUID subjectId) {

    }
}
