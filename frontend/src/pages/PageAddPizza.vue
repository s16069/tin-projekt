<template>
  <section class="main" id="main-add-order">
    <nav>
      <router-link to="/" class="button return-button">Wróć</router-link>
    </nav>
    <header>
      <h2 class="main-header">Dodaj do zamówienia</h2>
    </header>
    <div id="pizza-info">
      <img src="../assets/pizza.png" width="100px" height="100px" alt="pizza image" />

      <h4 id="pizza-name">{{ orderPosition.pizzaType.name }}</h4>

      <p>{{ orderPosition.pizzaType.description }}</p>
      <p>Pikantna: {{orderPosition.pizzaType.isSpicy ? 'tak' : 'nie'}}</p>
      <p>Wege: {{orderPosition.pizzaType.isVegetarian ? 'tak' : 'nie'}}</p>
    </div>

    <h3>Opcje</h3>
    <ValidationObserver ref="observer" v-slot="{ handleSubmit, errors }">
      <form @submit.prevent="handleSubmit(saveData); submitted=true;" novalidate>
        <validation-provider name="Ciasto" v-slot="{ errors }" class="row">
          <label for="dough" class="required">Ciasto:</label>
          <select id="dough" v-model="orderPosition.dough">
            <option value="null" selected disabled>Wybierz ciasto</option>
            <option v-for="dough in doughs" :key="dough">{{dough}}</option>
          </select>
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="Rozmiar" v-slot="{ errors }" class="row">
          <label for="size" class="required">Rozmiar:</label>
          <select id="size" v-model="orderPosition.size">
            <option value="null" selected disabled>Wybierz rozmiar</option>
            <option v-for="size in sizes" :key="size">{{size}}</option>
          </select>
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="Ilość" v-slot="{ errors }" class="row">
          <label for="basePrice" class="required">Ilość:</label>
          <input
            type="number"
            id="number"
            v-model="orderPosition.amount"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz ilość"
            required
            min="1"
            max="10"
            step="1"
          />
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <h3 class="both-columns" id="pizza-price">Cena: {{ price }}</h3>

        <button class="both-columns">Dodaj do zamówienia</button>

        <ul class="errors" v-if="submitted">
          <li v-for="error in Object.values(errors).flat(1)" :key="error">{{error}}</li>
        </ul>
      </form>
    </ValidationObserver>
  </section>
</template>

<script>
import { mapGetters } from "vuex";

import Menu from "./../components/Menu.vue";
import Api from "../api";

export default {
  name: "PageEditPizza",
  data: function() {
    return {
      orderPosition: {
        amount: 1,
        dough: undefined,
        size: undefined,
        pizzaType: {
          id: 0,
          name: "",
          description: "",
          isVegetarian: false,
          isVegan: false,
          isSpicy: false,
          basePrice: 0,
          image: null,
          pizzaPrices: []
        }
      },
      edit: false,
      submitted: false
    };
  },
  mounted: async function() {
    this.$store.dispatch("menu/listPizzas");
    this.$store.dispatch("menu/listSizes");
    this.$store.dispatch("menu/listDoughs");

    const pizzaId = this.$route.params.pizzaId;
    if (pizzaId) {
      this.edit = true;
      this.orderPosition.pizzaType = await Api.getPizza(pizzaId);
    }
    const index = this.$route.params.index;
    if(index) {
      this.edit = true;
      
    }
    
  },
  props: {},
  computed: {
    pizzas() {
      return this.$store.state.menu.pizzas;
    },
    sizes() {
      return this.$store.state.menu.sizes;
    },
    doughs() {
      return this.$store.state.menu.doughs;
    },
    price() {
      for (const priceOption of this.orderPosition.pizzaType.pizzaPrices) {
        if (
          this.orderPosition.size === priceOption.size &&
          this.orderPosition.dough === priceOption.dough
        ) {
          return priceOption.price * this.orderPosition.amount;
        }
      }
      return undefined;
    }
  },
  methods: {
    saveData() {
      this.$store.commit('order/addPizzaOrder', this.orderPosition)

			this.$router.push('/cart')
    }
  },
  components: {}
};
</script>

<style scoped>
#main-add-order {
  margin: 30px auto;
  width: 600px;
}

#pizza-info {
  margin: 10px;
  display: grid;
  grid-template-columns: 50% 50%;
  grid-gap: 10px;
}

#pizza-info img {
  grid-row: 1 / span 4;
  margin: 0 auto;
}

#pizza-name {
  margin-left: 2em;
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

#pizza-info input {
  width: 100%;
}

#pizza-price {
  margin: 20px 0 10px 0;
}

.return-button {
  float: right;
}
</style>
