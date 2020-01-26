<template>

<article class="menu-pizza">
	<header>
		<img src="../assets/pizza.png" width="200px" height="200px" alt="pizza image">
		<h4 class="pizza-name">{{pizza.name}}</h4>
	</header>
	<details>
		<summary>Opis: {{pizza.description}}</summary>
		<p>Pikantna: {{ pizza.isSpicy ? 'tak' : 'nie' }}</p>
		<p>Wegetariańska: {{ pizza.isVegetarian ? 'tak' : 'nie' }}</p>
		<p>Wegańska: {{ pizza.isVegan ? 'tak' : 'nie' }}</p>
		<p>Cena: {{ pizza.basePrice }} zł</p>
	</details>
	<div class="flex-grow"></div>
	<button v-if="orderEnabled" @click="onAddOrderClicked">Dodaj do zamówienia</button>
</article>

</template>

<script>
export default {
	name: 'PizzaCard',
	data: function() {
		return {}
	},
	props: ['pizza', 'orderEnabled'],
	computed: {
	},
	methods: {
		onAddOrderClicked: function() {
			this.$router.push(`/order/pizza/${this.pizza.id}`)
		}
	}
}
</script>

<style lang="scss" scoped>

.menu-pizza {
	background: #e8e8e8;
	margin: 10px;
	padding: 10px;
	border: 2px solid #ccc;
	width: 222px;
	display: flex;
	flex-direction: column;

	img {
		margin: auto;
	}

	.pizza-name {
		text-align: center;
	}

	a.button {
		width: 100%;
		margin-top: 10px;
		display: block;
	}
}
</style>
