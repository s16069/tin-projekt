package tin.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import tin.backend.dto.OrderDto;
import tin.backend.model.Order;
import tin.backend.model.OrderStatus;
import tin.backend.model.RoleNames;
import tin.backend.model.Views;
import tin.backend.service.OrderService;
import tin.backend.service.UserService;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Secured({RoleNames.ROLE_CLIENT, RoleNames.ROLE_EMPLOYEE, RoleNames.ROLE_ADMIN})
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@PostMapping
	@JsonView(Views.Overview.class)
	public ResponseEntity<Order> addOrder(@RequestBody @Valid OrderDto orderDto, Authentication authentication) {

		String login = authentication.getName();

		Optional<Order> order = orderService.addOrder(login, orderDto.getPizzas(), orderDto.getAddress(), orderDto.getComments());
		
		return ResponseEntity.of(order);
	}

	@GetMapping
	@JsonView(Views.Overview.class)
	public List<Order> listOrders(Authentication authentication) {

		String login = authentication.getName();

		return orderService.list(login);
	}

	@GetMapping("/{orderId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<Order> getOrder(Authentication authentication, @PathVariable long orderId) {

		String login = authentication.getName();

		return ResponseEntity.of(orderService.get(login, orderId));
	}

	@PutMapping("/{orderId}/status")
	@JsonView(Views.Detail.class)
	@Secured({RoleNames.ROLE_EMPLOYEE, RoleNames.ROLE_ADMIN})
	public ResponseEntity<Order> changeOrderStatus(@PathVariable long orderId, @RequestParam OrderStatus status) {

		return ResponseEntity.of(orderService.changeOrderStatus(orderId, status));
	}
}