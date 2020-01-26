package tin.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tin.backend.model.*;
import tin.backend.service.MenuService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

;

@RestController
@RequestMapping("/admin")
@Secured(RoleNames.ROLE_ADMIN)
public class AdminController {

	@Autowired
	private MenuService menuService;

	@GetMapping("/pizzas")
	@JsonView(Views.Overview.class)
	public ResponseEntity<List<PizzaType>> listPizzas() {

		List<PizzaType> pizzas = menuService.listPizzas();

		return ResponseEntity.ok().body(pizzas);
	}

	@GetMapping("/pizzas/{id}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaType> getPizza(@PathVariable("id") long id) {
		Optional<PizzaType> pizza = menuService.getPizza(id);
		return ResponseEntity.of(pizza);
	}

	@PostMapping("/pizzas")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaType> addPizza(@RequestBody @Valid PizzaType pizzaType) {
		PizzaType pizza = menuService.add(pizzaType);
		return ResponseEntity.ok().body(pizza);
	}

	@PutMapping("/pizzas/{id}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaType> updatePizza(@PathVariable("id") long id, @RequestBody @Valid PizzaType update) {
		Optional<PizzaType> pizza = menuService.update(id, update);
		return ResponseEntity.of(pizza);
	}

	@DeleteMapping("/pizzas/{id}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<Void> deletePizza(@PathVariable("id") long id) {
		menuService.delete(id);
		return ResponseEntity.ok().body(null);
	}


	@PostMapping("/pizzas/{pizzaId}/prices")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaPrice> addPrice(@PathVariable("pizzaId") long pizzaId, @RequestBody @Valid PizzaPrice pizzaPrice) {
		return ResponseEntity.of(menuService.addPrice(pizzaId, pizzaPrice));
	}

	@PutMapping("/pizzas/{pizzaId}/prices/{priceId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaPrice> updatePrice(@PathVariable("pizzaId") long pizzaId,  @PathVariable("priceId") long priceId, @RequestBody @Valid PizzaPrice update) {
		return ResponseEntity.of(menuService.updatePrice(pizzaId, priceId, update));
	}

	@DeleteMapping("/pizzas/{pizzaId}/prices/{priceId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<Void> deletePrice(@PathVariable("pizzaId") long pizzaId, @PathVariable("priceId") long priceId) {
		menuService.deletePrice(pizzaId, priceId);
		return ResponseEntity.ok().body(null);
	}
}