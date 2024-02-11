package trading.mappings;

import org.mapstruct.Mapper;
import trading.entities.UserEntity;
import trading.models.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserToUserEntityMapping {
    User userEntityToUser(UserEntity userEntity);
    UserEntity userToUserEntity(User user);
    List<User> userEntityToUser(List<UserEntity> userEntity);
    List<UserEntity> userToUserEntity(List<User> user);
}
