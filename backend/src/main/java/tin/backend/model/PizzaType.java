package tin.backend.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "PizzaType")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PizzaType {

	@Id
	@Getter
	@Setter
	@GeneratedValue
	@Column(name = "id")
	@JsonView(Views.Overview.class)
	protected Long id;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	protected String name;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	protected String description;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	protected Boolean isVegetarian = false;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	protected Boolean isVegan = false;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	protected Boolean isSpicy = false;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	private BigDecimal basePrice;

	@Getter
	@Setter
	@Column(nullable = true)
	@JsonView(Views.Overview.class)
	protected String image;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pizzaType")
	@OrderBy("id")
	@JsonView(Views.Detail.class)
	private List<PizzaPrice> pizzaPrices;

	public PizzaType(String name, String description, BigDecimal basePrice) {
		this.name = name;
		this.description = description;
		this.basePrice = basePrice;

		generatePrices();
	}

	private void generatePrices() {
		this.pizzaPrices = new ArrayList<>();
		for (PizzaSize pizzaSize : PizzaSize.values()) {
			for (PizzaDough pizzaDough : PizzaDough.values()) {
				BigDecimal price = basePrice.multiply(pizzaSize.getPriceMultiplier()).multiply(pizzaDough.getPriceMultiplier());
				PizzaPrice pizzaPrice = new PizzaPrice(this, pizzaDough, pizzaSize, price);
				this.pizzaPrices.add(pizzaPrice);
			}
		}
	}
}
