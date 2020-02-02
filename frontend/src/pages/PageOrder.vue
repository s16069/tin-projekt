<template>
<section class="main">
	<header>
		<h2 class="main-header">Zamówienie</h2>
	</header>
			<ValidationObserver ref="observer" v-slot="{ handleSubmit, errors }">
    <form @submit.prevent="handleSubmit(sendOrder); submitted=true;" novalidate id="pizza-form">
		<section id="order-pizzas">
			<span>Pizza</span>
			<span>Ciasto</span>
			<span>Rozmiar</span>
			<span>Ilość</span>
			<span>Cena</span>
			
			<template v-for="(position, index) in order.positions">
			<span :key="'name'+index">{{position.pizzaType.name}}</span>
			<span :key="'dough'+index">{{position.dough}}</span>
			<span :key="'size'+index">{{position.size}}</span>
			<span :key="'amount'+index">{{position.amount}}</span>
			<span :key="'price'+index">{{position.price}}</span>
			</template>
		</section>

		<span>Cena: {{order.price}}</span>
		
		<section id="order-address-container">
			<section id="order-address">
				<h4 class="both-columns">Dostawa:</h4>
				
				<span class="row">
          <label for="city" class="">Miejscowość:</label>
          <input 
						type="text"
						v-model="order.address.city"
						disabled>
        </span>
        
        <span class="row">
          <label for="street" class="">Ulica:</label>
          <input
            type="text"
            id="street"
            v-model="order.address.street"
						disabled>
        </span>
        
        <span class="row">
          <label for="homeNr" class="">Nr domu:</label>
          <input
            type="text"
            id="homeNr"
            v-model="order.address.homeNr"
						disabled>
        </span>
        
        <span class="row">
          <label for="flatNr">Nr mieszkania:</label>
          <input
            type="text"
            id="flatNr"
            v-model="order.address.flatNr"
						disabled>
       </span>

			<h4 class="both-columns">Historia:</h4>

				<span class="row">
          <label for="timeCreated">Data utworzenia:</label>
          <input
            type="text"
            id="timeCreated"
            v-bind:value="getDate(order.timeCreated)"
						disabled>
        </span>
				<span class="row">
          <label for="timeAccepted">Data zaakceptowania:</label>
          <input
            type="text"
            id="timeAccepted"
            v-bind:value="getDate(order.timeAccepted)"
						disabled>
        </span>
				<span class="row">
          <label for="timePrepared">Data przygotowania:</label>
          <input
            type="text"
            id="timePrepared"
            v-bind:value="getDate(order.timePrepared)"
						disabled>
        </span>
				<span class="row">
          <label for="timeDelivered">Data dostarczenia:</label>
          <input
            type="text"
            id="timeDelivered"
            v-bind:value="getDate(order.timeDelivered)"
						disabled>
        </span>
			</section>
		</section>
		<section id="order-comments">
			<label for="comments">Uwagi:</label>
			<textarea id="comments" rows=2 v-model="order.comments" disabled></textarea>
		</section>
	</form>
</ValidationObserver>
</section>
</template>

<script>
import { mapState, mapGetters } from 'vuex'

import Api from './../api'

export default {
	name: 'PageOrder',
	data: function() {
		return {
			order: {
				address: {},
				positions: [],
			}
		}
	},
	props: {
	},
	computed: {
		user: function() {
			return this.$store.state.person
		},
		orderPositions: function() {
			return this.$store.state.order.orderPositions
		},
		totalPrice: function() {
			const self = this
			return this.$store.state.order.order.pizzas.reduce(function(a, p) {
				return a += self.pizzaPrice(p)
			}, 0)
		},
	},
	async mounted() {
		const orderId = this.$route.params.orderId;
		this.order = await Api.getOrder(orderId);
	},
	methods: {
		getDate(timestamp){
			if(!timestamp)
				return '';
			const iso = new Date(timestamp).toISOString();
			return iso.substring(0, 10) + ' ' + iso.substring(12, 16);
		},
	},
	components: {
	}
}
</script>

<style scoped>
#order-pizzas {
	margin: 10px;
	padding: 10px;
	display: grid;
	grid-template-columns: 20% repeat(4, 1fr);
	grid-gap: 10px;
	background-color: #e8e8e8;
}

#order-address-container {
	width: 100%;
	display: inline-block;
}

#order-address {
	margin: 10px;
	padding: 10px;
	background-color: #e8e8e8;
}

#order-address label {
	margin: 3px 5px;
	font-size: 1.2em;
}

#order-address .both-columns {
	grid-column: 1 / span 2;
	text-align: center;
}

#order-payment-container {
	width: 40%;
	display: inline-block;
	vertical-align: top;
}

#order-payment {
	margin: 10px;
	padding: 10px;
	text-align: center;
	background-color: #e8e8e8;
}

#order-comments textarea {
	width: 100%;
}

#send-order {
	font-size: 1.2em;
}

.center-button {
	margin: 20px;
	text-align: center;
}
</style>
