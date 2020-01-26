<template>
<section class="main">
	<header>
		<h2 class="main-header">Edycja menu</h2>
	</header>
	<section id="edit-menu">
		<span>Pizza</span>
		<span>Nazwa</span>
		<span>Opis</span>
		<span>Pikantna</span>
		<span>Wege</span>
		<span>Cena bazowa</span>
		<span>Edytuj</span>
		<span>Usuń</span>
		
		<template v-for="pizza in pizzas">
			<img src="../assets/pizza.png" width="100px" height="100px" alt="pizza image" :key="'img'+pizza.id">
			<span :key="'name'+pizza.id">{{pizza.name}}</span>
			<span :key="'desc'+pizza.id">{{pizza.description}}</span>
			<span :key="'vege'+pizza.id">{{pizza.isSpicy ? 'tak' : 'nie'}}</span>
			<span :key="'vege2'+pizza.id">{{pizza.isVegetarian ? 'tak' : 'nie' }}</span>
			<span :key="'price'+pizza.id">{{ pizza.basePrice }} zł</span>
			<img src="../assets/edit.png" width="24px" hegth="24px" @click="onEditClicked(pizza.id)" :key="'edit'+pizza.id">
			<img src="../assets/delete.png" width="24px" hegth="24px" @click="onDeleteClicked(pizza.id)" :key="'del'+pizza.id">
		</template>
		
	</section>
	<section class="center-button">
		<router-link to="/admin/pizza/" tag="button">Dodaj pizzę</router-link>
	</section>
</section>
</template>

<script>
import { mapGetters } from 'vuex'

import Menu from './../components/Menu.vue'
import Api from '../api'

export default {
	name: 'PageEditMenu',
	data: function() {
		return {
		}
	},
	mounted: function() {
		this.$store.dispatch("menu/listPizzas");
	},
	props: {

	},
	computed: {
		pizzas: function() {
      return this.$store.state.menu.pizzas;
    }
	},
	methods: {
		onEditClicked(pizzaId) {
			this.$router.push(`/admin/pizza/${pizzaId}`)
		},
		async onDeleteClicked(pizzaId) {
			try {
				await Api.deletePizza(pizzaId);

				this.$store.dispatch("menu/listPizzas");
			} catch(error) {
				console.error(error);
			}
		}
	},
	components: {
	}
}
</script>

<style scoped>
#edit-menu {
	margin: 10px;
	padding: 10px;
	display: grid;
	grid-template-columns: 150px repeat(7, 1fr);
	grid-gap: 10px;
	background-color: #e8e8e8;
}

#edit-menu a.button {
	height: 30px;
}

.center-button {
	margin: 20px;
	text-align: center;
}
</style>
