package ua.ivan.provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ua.ivan.provider.model.Role;
import ua.ivan.provider.model.Status;
import ua.ivan.provider.model.User;
import ua.ivan.provider.service.UserDetailsServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTest {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    UserDetailsServiceImpl userDetailsService;

    @Test
    void getUserByEmailTest() {
        assertEquals(userDetailsService.getUserByEmail("admin@mail.com").getId(), 46L);
    }
    @Test
    void findAllTest() {
        assertEquals(userDetailsService.findAll().get(0).getEmail(), "user@mail.com");
    }

    @Test
    void saveUserTest() {
        User user = new User();
        user.setEmail("1");
        user.setPassword("2");
        user.setFirstName("3");
        user.setLastName("4");
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setBalance(0);
        assertEquals(userDetailsService.saveUser(user).getEmail(), user.getEmail());
        userDetailsService.deleteUser(user.getId());

    }

}
