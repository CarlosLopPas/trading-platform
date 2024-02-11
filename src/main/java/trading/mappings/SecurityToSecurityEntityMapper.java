package trading.mappings;

import org.mapstruct.Mapper;
import trading.entities.SecurityEntity;
import trading.models.Security;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SecurityToSecurityEntityMapper {
    SecurityEntity securityToSecurityEntity(Security source);
    Security securityEntityToSecurity(SecurityEntity destination);
    List<SecurityEntity> securityToSecurityEntity(List<Security> source);
    List<Security> securityEntityToSecurity(List<SecurityEntity> destination);
}
