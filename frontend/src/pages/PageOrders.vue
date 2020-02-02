<template>
<section class="main">
	<header>
		<h2 class="main-header">Zamówienia</h2>
	</header>
		<section id="order-pizzas">
			<span>Data utworzenia</span>
			<span>Cena</span>
			<span>Status</span>
			<span>Szczegóły</span>
			
			<template v-for="(position, index) in orders">
			<span :key="'name'+index">{{getDate(position.timeCreated)}}</span>
			<span :key="'dough'+index">{{position.price}}</span>
			<span :key="'size'+index" v-if="!isEmployee">{{position.status}}</span>
			<span :key="'size'+index" v-if="isEmployee">
				<select v-model="position.status">	
					<option>CREATED</option>
					<option>ACCEPTED</option>
					<option>PREPARED</option>
					<option>DELIVERED</option>
				</select>
				<button @click="onUpdateStatusClicked(index)">Aktualizuj status</button>
			</span>
			<img src="../assets/details.png" width="24px" hegth="24px" @click="onDetailsClicked(position)" :key="'edit'+index">
			</template>
		</section>
		
</section>
</template>

<script>
import { mapState, mapGetters } from 'vuex'

import Api from './../api'

export default {
	name: 'PageOrders',
	data: function() {
		return {
			orders: []
		}
	},
	props: {
	},
	computed: {
		...mapGetters('user', ['isEmployee']),
	},
	async mounted() {
    this.orders = await Api.getOrders();
  },
	methods: {
		getDate(timestamp){
			if(!timestamp)
				return '';
			return new Date(timestamp).toISOString().substring(0, 10);
		},
		onUpdateStatusClicked: async function(index) {
			const order = this.orders[index];
			
			try {
				const newOrder = await Api.setOrderStatus(order.id, order.status);

				this.orders[index] = newOrder;
			} catch(error) {
				console.error(error);
				
				alert('Nie można zmienić statusu na wcześniejszy');
			}
		},
		onDetailsClicked: function(order) {
			this.$router.push(`/order/${order.id}`);
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
	grid-template-columns: 25% repeat(3, 1fr);
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
