<template>
<section class="main" id="main-add-order">
	<nav>
		<router-link to="/admin/menu" class="button return-button">Wróć</router-link>
	</nav>
	<header>
		<h2 class="main-header">Edycja pizzy</h2>
	</header>
	<ValidationObserver ref="observer" v-slot="{ handleSubmit, errors }">
    <form @submit.prevent="handleSubmit(saveData); submitted=true;" novalidate id="pizza-form">
		<div class="center-text">
			<img src="../assets/pizza.png" width="100px" height="100px" alt="pizza image">
			<br>
			<button @click="onChangePicClicked">Zmień zdjęcie</button>
		</div>
		
		<section id="pizza-info">

			<validation-provider name="Nazwa" v-slot="{ errors }" class="row">
				<label for="name">Nazwa:</label>
				<input
					type="text"
					id="name"
					v-model="pizza.name"
					v-bind:class="{ invalid: errors.length > 0 }"
					placeholder="Wpisz nazwę"
					required
					minlength="3"
					maxlength="30">
				<span class="error">{{ errors[0] }}</span>
			</validation-provider>
        
        <validation-provider name="Opis" v-slot="{ errors }" class="row">
          <label for="description">Opis:</label>
          <input
            type="text"
            id="description"
            v-model="pizza.description"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz opis"
						required
						minlength="3"
            maxlength="50">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="Cena bazowa" v-slot="{ errors }" class="row">
          <label for="basePrice">Cena bazowa:</label>
          <input
            type="number"
            id="basePrice"
            v-model="pizza.basePrice"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz cenę bazową"
            required
						min="0"
						max="100"
						step="0.01">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
			
			<div class="row">
				<label for="spicy">Pikantna:</label>
				<span><input type="checkbox" id="spicy" v-model="pizza.isSpicy"></span>
			</div>
			<div class="row">
				<label for="wege">Wege:</label>
				<span><input type="checkbox" id="wege" v-model="pizza.isVegetarian"></span>
			</div>
			<div class="row">
				<label for="wege">Wegan:</label>
				<span><input type="checkbox" id="wegan" v-model="pizza.isVegan"></span>
			</div>
		</section>
		<section id="prices-header">
			<h4>Ceny wariantów:</h4>
			<button @click="onAddPriceClicked">Dodaj wariant</button>
			<button @click="onAddSuggestedPricesClicked">Dodaj sugerowane warianty</button>
		</section>
		<section id="prices">
			<span>Ciasto</span>
			<span>Rozmiar</span>
			<span>Cena</span>
			<span>Usuń</span>

			<template v-for="(price, index) in pizza.pizzaPrices">
			
			<select v-model="price.dough" :key="'dough' + index">
				<option value="" disabled>Wybierz ciasto</option>
				<option v-for="dough in doughs" :key=dough>{{dough}}</option>
			</select>
			
			<select v-model="price.size" :key="'size' + index">
				<option value="" disabled>Wybierz rozmiar</option>
				<option v-for="size in sizes" :key=size>{{size}}</option>
			</select>

			<input
				type="number" 
				v-model="price.price"
				min="0" 
				max="100"
				step="0.01"
				required
				:key="'price' + index">

				<img src="../assets/delete.png" width="24px" hegth="24px" @click="onDeletePriceClicked(index)" :key="'delete' + index">

			</template>
		</section>
		
		<button class="both-columns">Zapisz</button>
		
		<ul class="errors" v-if="submitted">
			<li v-for="error in Object.values(errors).flat(1)" :key="error">{{error}}</li>
		</ul>
	</form>
	</ValidationObserver>
</section>
</template>

<script>
import { mapGetters } from 'vuex'

import Menu from './../components/Menu.vue'
import Api from '../api'

export default {
	name: 'PageEditPizza',
	data: function() {
		return {
			pizza: {
				id: 0,
				name: '',
				description: '',
				isVegetarian: false,
				isVegan: false,
				isSpicy: false,
				basePrice: 0,
				image: null,
				pizzaPrices: [],
			},
			edit: false,
			submitted: false,
		}
	},
	mounted: async function() {
		this.$store.dispatch("menu/listPizzas");
		this.$store.dispatch("menu/listSizes");
		this.$store.dispatch("menu/listDoughs");

		const pizzaId = this.$route.params.pizzaId;
		if (pizzaId) {
			this.edit = true;
			this.pizza = await Api.getPizza(pizzaId);
		}
	},
	props: {

	},
	computed: {
		pizzas: function() {
      return this.$store.state.menu.pizzas;
		},
		sizes: function() {
      return this.$store.state.menu.sizes;
		},
		doughs: function() {
      return this.$store.state.menu.doughs;
    },
	},
	methods: {
		async saveData() {
			const promise = this.edit ? Api.updatePizza(this.pizza.id, this.pizza) : Api.savePizza(this.pizza)
			try {
				const result = await promise;

				this.$router.push(`/admin/menu`)
			} catch(error) {
				console.error(error);
			}
		},
		onChangePicClicked(ev) {
			ev.preventDefault();
		},
		onAddPriceClicked(ev) {
			ev.preventDefault()
			this.pizza.pizzaPrices.push({});
		},
		onAddSuggestedPricesClicked(ev) {
			ev.preventDefault()
			this.pizza.pizzaPrices.splice(0, this.pizza.pizzaPrices.length);

			for (const size of this.sizes) {
				for (const dough of this.doughs) {
					const price = this.pizza.basePrice;
					this.pizza.pizzaPrices.push({
						size,
						dough,
						price
					});
				}
			}
		},
		onDeletePriceClicked(index) {
			this.pizza.pizzaPrices.splice(index, 1);
		}
	},
	components: {
	}
}
</script>

<style scoped>
#main-add-order {
	margin: 10px;
	padding: 10px;
}

#pizza-form {
	margin: 10px;
	display: grid;
	grid-template-columns: 220px 1fr;
	grid-gap: 10px;
}

#pizza img {
	grid-row: 1 / span 4;
	margin: 0 auto;
}

#pizza button {
	margin: 5px 0;
	width: 100%;
}

#pizza-info {
	margin: 10px;
}

#pizza input[type=number] {
	width: 100%;
}

.both-columns {
	grid-column: 1 / span 2;
	text-align: center;
}

#pizza-info label {
	margin: 3px 5px;
	text-align: center;
	font-size: 1em;
}

#prices {
	padding: 10px;
	grid-column: 1 / span 2;
	display: grid;
	grid-template-columns: 1fr 1fr 1fr 50px;
	grid-gap: 10px;
	background-color: #fff;
}

#prices-header {
	grid-column: 1 / span 2;
	margin-top: 10px;
	padding: 10px;
	display: grid;
	grid-template-columns: 1fr 1fr 1fr;
	grid-gap: 10px;
}

#prices input[type="number"] {
	width: 90%;
}

.return-button {
	float: right;
}

</style>
