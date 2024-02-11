package trading.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import trading.dao.SecurityRepository;
import trading.entities.SecurityEntity;
import trading.mappings.SecurityToSecurityEntityMapper;
import trading.models.Security;
import trading.services.SecurityService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SecurityServiceUTest {
    private SecurityRepository repo = mock(SecurityRepository.class);
    private SecurityToSecurityEntityMapper mapper = mock(SecurityToSecurityEntityMapper.class);
    private SecurityService service = new SecurityService(repo, mapper);

    @Test
    public void testSearchObject() {
        Long id = 1L;
        String name = "test";
        SecurityEntity entity = new SecurityEntity(id, name);
        Security model = new Security(id, name);

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.securityEntityToSecurity(entity)).thenReturn(model);

        Security resp = service.searchForSecurity(1L);

        assertNotNull(resp);
        assertEquals(model, resp);
    }

    @Test
    public void testCreateObject() {
        Long id = 1L;
        String name = "test";
        SecurityEntity entity = new SecurityEntity(id, name);
        Security model = new Security(id, name);
        List<SecurityEntity> entitiesList = List.of(entity);
        List<Security> modelList = List.of(model);

        when(mapper.securityToSecurityEntity(modelList)).thenReturn(entitiesList);
        when(repo.saveAll(entitiesList)).thenReturn(entitiesList);

        service.createSecurities(modelList);

        verify(repo).saveAll(entitiesList);
    }

    @Test
    public void testCreateNullUser() {
        Assertions.assertThrows(NullPointerException.class, () -> service.createSecurities(null));
    }

    @Test
    public void testCreateNullListUsers() {
        Assertions.assertThrows(NullPointerException.class, () -> service.createSecurities(null));
    }

    @Test
    public void testDeleteNullListUsers() {
        Assertions.assertThrows(NullPointerException.class, () -> service.deleteSecurities(null));
    }

    @Test
    public void testDelete() {
        List<Security> securityModels = List.of(new Security(1L, "name"));
        List<SecurityEntity> securityEntities = List.of(new SecurityEntity(1L, "name"));

        when(mapper.securityToSecurityEntity(securityModels)).thenReturn(securityEntities);

        service.deleteSecurities(securityModels);

        verify(repo).deleteAll(securityEntities);
    }
}
