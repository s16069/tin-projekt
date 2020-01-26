package tin.backend.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sun.org.apache.bcel.internal.generic.POP;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.Assert;
import tin.backend.dao.PizzaTypeDao;
import tin.backend.dao.OrderDao;
import tin.backend.dao.OrderedPizzaDao;
import tin.backend.dao.UserDao;
import tin.backend.model.*;
import tin.backend.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
@Transactional(readOnly = false)
public class OrderService {

	@Autowired
	private PizzaTypeDao pizzaTypeDao;
	
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderedPizzaDao orderedPizzaDao;
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public Optional<Order> addOrder(String login, List<OrderedPizza> orderedPizzas, Address address, String comments) {
		User user = userDao.findByLogin(login);
		
		Order order = new Order();

		List<OrderedPizza> positions = new ArrayList<>();
		for (OrderedPizza inOrderedPizza : orderedPizzas) {
			Long orderedPizzaId = inOrderedPizza.getPizzaType().getId();
			if(orderedPizzaId == null) {
				return Optional.empty();
			}

			Optional<PizzaType> optionalPizzaType = pizzaTypeDao.findById(orderedPizzaId);
			if(!optionalPizzaType.isPresent()) {
				return Optional.empty();
			}

			PizzaType pizzaType = optionalPizzaType.get();

			PizzaSize size = inOrderedPizza.getSize();

			PizzaDough dough = inOrderedPizza.getDough();

			int amount = inOrderedPizza.getAmount();

			OrderedPizza orderedPizza = new OrderedPizza();
			orderedPizza.setOrder(order);
			orderedPizza.setAmount(amount);
			orderedPizza.setPizzaType(pizzaType);
			orderedPizza.setDough(dough);
			orderedPizza.setSize(size);

			positions.add(orderedPizza);
		}

		setStatus(order, OrderStatus.CREATED);
		order.setClient(user);
		order.setAddress(address);
		order.setComments(comments);

		orderDao.saveAndFlush(order);

		order.setPositions(positions);

		orderedPizzaDao.saveAll(positions);


		orderDao.saveAndFlush(order);

		return orderDao.findById(order.getId());
	}

	public static List<Order> listNewOrders(Session session) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = criteriaBuilder.createQuery(Order.class);

		Root<Order> orderRoot = criteria.from(Order.class);
		criteria.select(orderRoot);
		criteria.where(criteriaBuilder.equal(orderRoot.get("status"), OrderStatus.CREATED));

		return session.createQuery(criteria).getResultList();
	}

	public List<Order> list(String login) {
		User user = userDao.findByLogin(login);

		boolean client = user.getUserRole() == UserRole.ROLE_CLIENT;

		if(client) {
			return orderDao.findByClient(user);
		} else {
			Timestamp from = Timestamp.from(Instant.now().minus(1, ChronoUnit.DAYS));
			Timestamp to = Timestamp.from(Instant.now());
			return orderDao.findByTimeCreatedBetween(from, to);
		}
	}

	public Optional<Order> get(String login, long orderId) {
		User user = userDao.findByLogin(login);

		Optional<Order> order = orderDao.findById(orderId);

		if (!order.isPresent() || !canView(user, order.get())) {
			return Optional.empty();
		}

		return order;
	}

	private boolean canView(User user, Order order) {

		boolean client = user.getUserRole() == UserRole.ROLE_CLIENT;

		return !client || order.getClient().getId().equals(user.getId());
	}

	@Transactional
	public Optional<Order> changeOrderStatus(long orderId, OrderStatus newStatus) {
		Optional<Order> optional = orderDao.findById(orderId);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		Order order = optional.get();

		OrderStatus currentStatus = order.getStatus();
		int currentOrdinal = currentStatus.ordinal();
		int newOrdinal = newStatus.ordinal();

		if(newOrdinal != currentOrdinal + 1) {
			throw new IllegalArgumentException("Nie można zmienić stanu z " + currentStatus + " na " + newStatus);
		}

		setStatus(order, newStatus);

		return Optional.of(orderDao.saveAndFlush(order));
	}

	private void setStatus(Order order, OrderStatus newStatus) {
		order.setStatus(newStatus);
		Timestamp timestamp = Timestamp.from(Instant.now());
		switch (newStatus) {
			case CREATED:
				order.setTimeCreated(timestamp);
				break;
			case ACCEPTED:
				order.setTimeAccepted(timestamp);
				break;
			case PREPARED:
				order.setTimePrepared(timestamp);
				break;
			case DELIVERED:
				order.setTimeDelivered(timestamp);
				break;
		}
	}

	public void asdf(OrderedPizza probe, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher
				.matchingAll()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
				.withIgnoreNullValues();
		Example<OrderedPizza> example = Example.of(probe, matcher);

		orderedPizzaDao.findAll(example, pageable);

		Specification<OrderedPizza> spec = new ExampleSpecification<>(example);

		orderedPizzaDao.findAll(spec, pageable);
	}

	private static class ExampleSpecification<T> implements Specification<T> {

		private static final long serialVersionUID = 1L;

		private final Example<T> example;

		ExampleSpecification(Example<T> example) {

			Assert.notNull(example, "Example must not be null!");

			this.example = example;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			return QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
		}
	}
}
