package trading.api;

import org.springframework.web.bind.annotation.*;
import trading.models.Security;
import trading.services.SecurityService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/security")
public class SecurityRest {
    private final SecurityService service;

    public SecurityRest(SecurityService service) {
        this.service = service;
    }

    @GetMapping
    public Security getItem(@RequestBody Long id) {
        Objects.requireNonNull(id);
        return service.searchForSecurity(id);
    }

    @PostMapping
    public void createSecurities(@RequestBody List<Security> securities) {
        service.createSecurities(securities);
    }

    @DeleteMapping
    public void deleteSecurities(@RequestBody List<Security> securities) {
        service.deleteSecurities(securities);
    }
}
