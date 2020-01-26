package tin.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tin.backend.model.PizzaPrice;
import tin.backend.model.PizzaType;

@Repository
public interface PizzaPriceDao extends JpaRepository<PizzaPrice, Long> {

}
