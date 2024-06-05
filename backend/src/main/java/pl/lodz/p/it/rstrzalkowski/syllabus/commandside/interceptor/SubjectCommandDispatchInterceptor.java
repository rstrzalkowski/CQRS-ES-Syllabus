package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectAlreadyExistsCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@WriteApplicationBean
public class SubjectCommandDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final SubjectNameRepository subjectNameRepository;

    @Override
    @Transactional
    @Retryable(retryFor = SubjectAlreadyExistsCommandExecutionException.class)
    public @NotNull BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@NotNull List<? extends CommandMessage<?>> list) {
        return (i, m) -> {
            if (CreateSubjectCommand.class.equals(m.getPayloadType())) {
                final CreateSubjectCommand createSubjectCommand = (CreateSubjectCommand) m.getPayload();
                reserveSubjectName(createSubjectCommand.getName());
            }

            if (UpdateSubjectCommand.class.equals(m.getPayloadType())) {
                final UpdateSubjectCommand updateSubjectCommand = (UpdateSubjectCommand) m.getPayload();
                Optional<SubjectNameEntity> existingSubject = subjectNameRepository.findBySubjectName(updateSubjectCommand.getName());

                if (existingSubject.isPresent() && existingSubject.get().getAggregateId() != updateSubjectCommand.getSubjectId()
                        || existingSubject.isEmpty()) {
                    reserveSubjectName(updateSubjectCommand.getName());
                }
            }

            return m;
        };
    }

    private void reserveSubjectName(String name) {
        try {
            subjectNameRepository.save(new SubjectNameEntity(name));
        } catch (DataIntegrityViolationException sqle) {
            throw new SubjectAlreadyExistsCommandExecutionException();
        }
    }
}
