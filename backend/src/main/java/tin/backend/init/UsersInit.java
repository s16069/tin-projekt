package tin.backend.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tin.backend.model.User;
import tin.backend.model.UserRole;
import tin.backend.service.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class UsersInit {

    @Autowired
    private UserService userService;

    @PostConstruct
    @Transactional
    private void postConstruct() {

        if (userService.findAll().size() == 0) {
            addUsers();
        }

    }

    private void addUsers() {
        User admin = new User();
        admin.setUserRole(UserRole.ROLE_ADMIN);
        admin.setLogin("admin");
        admin.setPass("asdf");
        admin.setName("Admin");
        admin.setSurname("Admin");
        admin.setTel("123456789");
        admin.setEmail("a@a.com");
        userService.addUser(admin);

        User employee = new User();
        employee.setUserRole(UserRole.ROLE_EMPLOYEE);
        employee.setLogin("employee");
        employee.setPass("asdf");
        employee.setName("Employee");
        employee.setSurname("Employee");
        employee.setTel("123456789");
        employee.setEmail("a@a.com");
        userService.addUser(employee);

        User client = new User();
        client.setUserRole(UserRole.ROLE_CLIENT);
        client.setLogin("client");
        client.setPass("asdf");
        client.setName("Client");
        client.setSurname("Client");
        client.setTel("123456789");
        client.setEmail("a@a.com");
        userService.addUser(client);
    }
}
