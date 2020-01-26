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
			<span>Edytuj</span>
			<span>Usuń</span>
			
			<template v-for="(position, index) in orderPositions">
			<span :key="'name'+index">{{position.pizzaType.name}}</span>
			<span :key="'dough'+index">{{position.dough}}</span>
			<span :key="'size'+index">{{position.size}}</span>
			<span :key="'amount'+index">{{position.amount}}</span>
			<span :key="'price'+index">getPrice(position)</span>
			<img src="../assets/edit.png" width="24px" hegth="24px" @click="onEditPositionClicked(position)" :key="'edit'+index">
			<img src="../assets/delete.png" width="24px" hegth="24px" @click="onDeletePositionClicked(position)" :key="'delete'+index">
			</template>
		</section>
		
		<section class="center-button">
			<router-link to="/" tag="button">Dodaj inną pizzę do zamówienia</router-link>
		</section>
		
		<section id="order-address-container">
			<section id="order-address">
				<h4 class="both-columns">Dostawa:</h4>
				
				<validation-provider name="Miejscowość" v-slot="{ errors }" class="row">
          <label for="city" class="required">Miejscowość:</label>
          <select id="city" v-model="address.city" v-bind:class="{ invalid: errors.length > 0 }" required>
            <option selected disabled>Wybierz miejscowość</option>
            <option>Warszawa</option>
            <option>Łomianki</option>
          </select>
        </validation-provider>
        
        <validation-provider name="Ulica" v-slot="{ errors }" class="row">
          <label for="street" class="required">Ulica:</label>
          <input
            type="text"
            id="street"
            v-model="address.street"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz ulicę"
						required
            maxlength="50">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
        
        <validation-provider name="Nr domu" v-slot="{ errors }" class="row">
          <label for="homeNr" class="required">Nr domu:</label>
          <input
            type="text"
            id="homeNr"
            v-model="address.homeNr"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz nr domu"
						required
            maxlength="10">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
        
        <validation-provider name="Nr mieszkania" v-slot="{ errors }" class="row">
          <label for="flatNr">Nr mieszkania:</label>
          <input
            type="text"
            id="flatNr"
            v-model="address.flatNr"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz nr mieszkania"
            maxlength="10">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
			</section>
		</section>
		<section id="order-comments">
			<label for="comments">Uwagi:</label>
			<textarea id="comments" rows=2 v-model="comments"></textarea>
		</section>
		
		<section class="center-button">
			<button id="send-order">Złóż zamówienie</button>
		</section>
		
		<ul class="errors" v-if="submitted">
      <li v-for="error in Object.values(errors).flat(1)" :key="error">{{error}}</li>
    </ul>
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
			address: {},
			comments: '',
			submitted: false,
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
	methods: {
		onEditPositionClicked: function(pizza) {
			//todo
		},
		onDeletePositionClicked: function(pizza) {
			this.$store.commit('order/removePizzaOrder', pizza)

			if (this.orderPositions.length == 0)
				this.$router.push('/')
		},
		sendOrder: function() {
			const self = this
			Api.addOrder(this.orderPositions, this.address).then(function() {
				self.$store.commit('order/resetOrder')
				self.$router.push('/')

			})
		}
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
	grid-template-columns: 20% repeat(6, 1fr);
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
