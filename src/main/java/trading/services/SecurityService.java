package trading.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import trading.dao.SecurityRepository;
import trading.mappings.SecurityToSecurityEntityMapper;
import trading.models.Security;

import java.util.List;
import java.util.Objects;

@Service
public class SecurityService {
    private final SecurityRepository repo;
    private final SecurityToSecurityEntityMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    public SecurityService(SecurityRepository repo, SecurityToSecurityEntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Security searchForSecurity(long id) {
        log.info("Searching for security item with ID {}", id);
        return mapper.securityEntityToSecurity(
            repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No suitable object ID found")));
    }

    public void createSecurities(List<Security> securities) {
        Objects.requireNonNull(securities);

        if (securities.isEmpty()) {
            log.warn("Empty security objects list received while creating. Nothing done");
            return;
        }

        log.info("Creating given securities");
        log.debug("Given list: {}", securities);

        repo.saveAll(mapper.securityToSecurityEntity(securities));
    }

    public void deleteSecurities(List<Security> securities) {
        Objects.requireNonNull(securities);

        if (securities.isEmpty()) {
            log.warn("Empty security objects list received while deleting. Nothing done");
            return;
        }

        log.info("Deleting given securities");
        log.debug("Given list: {}", securities);

        repo.deleteAll(mapper.securityToSecurityEntity(securities));
    }
}
