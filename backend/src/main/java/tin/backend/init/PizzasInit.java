package tin.backend.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tin.backend.model.PizzaType;
import tin.backend.model.User;
import tin.backend.model.UserRole;
import tin.backend.service.MenuService;
import tin.backend.service.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
public class PizzasInit {

    @Autowired
    private MenuService menuService;

    @PostConstruct
    @Transactional
    private void postConstruct() {

        if(menuService.list().size() == 0) {
           addPizzas();
        }
    }

    private void addPizzas() {
        PizzaType p1 = new PizzaType("Carbonara", "Bardzo smaczna", new BigDecimal(20));

        menuService.add(p1);

        PizzaType p2 = new PizzaType("Cappriciosa", "Bardzo smaczna", new BigDecimal(25));

        menuService.add(p2);
    }
}