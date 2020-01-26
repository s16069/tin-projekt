package tin.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tin.backend.model.Order;
import tin.backend.model.User;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findByClient(User client);

    List<Order> findByTimeCreatedBetween(Timestamp from, Timestamp to);
}
