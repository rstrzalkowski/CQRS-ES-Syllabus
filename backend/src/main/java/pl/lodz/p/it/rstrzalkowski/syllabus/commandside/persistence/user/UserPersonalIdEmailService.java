package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserAlreadyRegisteredCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@WriteApplicationBean
public class UserPersonalIdEmailService {
    private final UserPersonalIdEmailRepository userPersonalIdEmailRepository;

    public void lockPersonalIdAndEmail(String personalId, String email) {
        try {
            userPersonalIdEmailRepository.save(new UserPersonalIdEmailEntity(email, personalId));
            userPersonalIdEmailRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyRegisteredCommandExecutionException();
        }
    }

    public void updateAggregateId(String email, UUID userId) {
        userPersonalIdEmailRepository.findByEmail(email).ifPresent(userPersonalIdEmailEntity -> {
            userPersonalIdEmailEntity.setAggregateId(userId);
            userPersonalIdEmailRepository.save(userPersonalIdEmailEntity);
            userPersonalIdEmailRepository.flush();
        });
    }

    public void releasePersonalIdAndEmail(String personalId) {
        userPersonalIdEmailRepository.findByPersonalId(personalId).ifPresent(userPersonalIdEmailRepository::delete);
        userPersonalIdEmailRepository.flush();
    }
}
