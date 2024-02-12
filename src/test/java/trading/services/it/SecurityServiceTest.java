package trading.services.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trading.models.Security;
import trading.services.SecurityService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SecurityServiceTest {
    @Autowired
    private SecurityService service;

    @Test
    public void testCreateAndSearchUser() {
        Security security = new Security(1L, "name");
        service.createSecurities(List.of(security));

        Security resp = service.searchForSecurity(1L);
        assertEquals(security, resp);
    }
}
