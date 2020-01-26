package tin.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;

import tin.backend.model.PizzaType;
import tin.backend.model.PizzaDough;
import tin.backend.model.PizzaSize;
import tin.backend.model.Views;
import tin.backend.service.MenuService;;

@RestController
@RequestMapping("/menu")
public class MenuController {

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
	
	@GetMapping("/sizes")
	public ResponseEntity<List<PizzaSize>> listSizes() throws JsonProcessingException {

		List<PizzaSize> sizes = menuService.listPizzaSizes();
		
		return ResponseEntity.ok().body(sizes);
	}
	
	@GetMapping("/doughs")
	public ResponseEntity<List<PizzaDough>> listDoughs() throws JsonProcessingException {

		List<PizzaDough> doughs = menuService.listPizzaDoughs();
		
		return ResponseEntity.ok().body(doughs);
	}
}