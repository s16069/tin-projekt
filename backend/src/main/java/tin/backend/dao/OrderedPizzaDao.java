package tin.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import tin.backend.model.OrderedPizza;

@Repository
public interface OrderedPizzaDao extends JpaRepository<OrderedPizza, Long>, JpaSpecificationExecutor {

}
