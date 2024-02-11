package trading.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import trading.dao.UserRepository;
import trading.entities.UserEntity;
import trading.mappings.UserToUserEntityMapping;
import trading.models.User;
import trading.services.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceUTest {
    private UserRepository repo;
    private UserToUserEntityMapping mapper;
    private UserService service;

    @Test
    public void testSearchById() {
        Long id = 1L;
        String name = "test";
        String password = "testpass";
        UserEntity entity = new UserEntity(id, name, password);
        User model = new User(id, name, password);

        //FIXME: Repeated on each test. Reason: @BeforeEach method is not invoked, maybe because
        // https://stackoverflow.com/questions/49441049/junit-5-does-not-execute-method-annotated-with-beforeeach
        repo = mock(UserRepository.class);
        mapper = mock(UserToUserEntityMapping.class);
        service = new UserService(repo, mapper);

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.userEntityToUser(entity)).thenReturn(model);

        User resp = service.searchById(1L);

        assertNotNull(resp);
        assertEquals(model, resp);
    }

    @Test
    public void testCreateObject() {
        Long id = 1L;
        String name = "test";
        String password = "testpass";
        UserEntity entity = new UserEntity(id, name, password);
        User model = new User(id, name, password);
        List<UserEntity> entityList = List.of(entity);
        List<User> modelList = List.of(model);

        repo = mock(UserRepository.class);
        mapper = mock(UserToUserEntityMapping.class);
        service = new UserService(repo, mapper);

        when(mapper.userToUserEntity(modelList)).thenReturn(entityList);
        when(repo.saveAll(entityList)).thenReturn(entityList);

        service.createUsers(modelList);

        verify(repo).saveAll(entityList);
    }

    @Test
    public void testCheckExistingUser() {
        Long id = 1L;
        String name = "test";
        String password = "testpass";
        UserEntity entity = new UserEntity(id, name, password);
        User model = new User(id, name, password);

        repo = mock(UserRepository.class);
        mapper = mock(UserToUserEntityMapping.class);
        service = new UserService(repo, mapper);

        when(repo.findByNameAndPassword(name, password)).thenReturn(entity);

        boolean result = service.checkUser(model);

        verify(repo).findByNameAndPassword(name, password);
        assertTrue(result);
    }

    @Test
    public void testCheckNotExistingUser() {
        Long id = 1L;
        String name = "test";
        String password = "testpass";
        User model = new User(id, name, password);

        repo = mock(UserRepository.class);
        mapper = mock(UserToUserEntityMapping.class);
        service = new UserService(repo, mapper);

        when(repo.findByNameAndPassword(name, password)).thenReturn(null);

        boolean result = service.checkUser(model);

        verify(repo).findByNameAndPassword(name, password);
        assertFalse(result);
    }

    @Test
    public void testCheckNullUser() {
        Assertions.assertThrows(NullPointerException.class, () -> service.checkUser(null));
    }

    @Test
    public void testCreateNullUser() {
        Assertions.assertThrows(NullPointerException.class, () -> service.createUsers(null));
    }

    @Test
    public void testCreateNullListUsers() {
        Assertions.assertThrows(NullPointerException.class, () -> service.createUsers(null));
    }

    @Test
    public void testDeleteNullListUsers() {
        Assertions.assertThrows(NullPointerException.class, () -> service.deleteUsers(null));
    }

    @Test
    public void testDelete() {
        List<User> userModels = List.of(new User(1L, "name", "pass"));
        List<UserEntity> userEntity = List.of(new UserEntity(1L, "name", "pass"));

        repo = mock(UserRepository.class);
        mapper = mock(UserToUserEntityMapping.class);
        service = new UserService(repo, mapper);

        when(mapper.userToUserEntity(userModels)).thenReturn(userEntity);

        service.deleteUsers(userModels);

        verify(repo).deleteAll(userEntity);
    }
}
