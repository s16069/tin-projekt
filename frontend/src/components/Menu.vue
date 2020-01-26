<template>
  <div class="menu">

    <section class="main-search">
      <input name="search" placeholder="Wyszukaj pizze" />
    </section>
    <header>
      <h2 class="main-header">Menu</h2>
    </header>
    <section id="menu">
      <div v-for="pizza in pizzas" :key="pizza.id" class="item">
        <pizza-card :pizza="pizza" :orderEnabled="orderEnabled" @orderPizza="orderPizza"></pizza-card>
      </div>
    </section>
  </div>
</template>

<script>
import PizzaCard from "./PizzaCard.vue";
export default {
  name: "Menu",
  data: function() {
    return {};
  },
  props: ["orderEnabled"],
  mounted: function() {
    this.$store.dispatch("menu/listPizzas");
    this.$store.dispatch("menu/listSizes");
    this.$store.dispatch("menu/listDoughs");
    //this.$store.dispatch("menu/listIngredients");
  },
  computed: {
    pizzas: function() {
      return this.$store.state.menu.pizzas;
    }
  },
  methods: {
    orderPizza: function(pizza) {
      this.$emit("orderPizza", pizza);
    }
  },
  components: {
    "pizza-card": PizzaCard
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.flex {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.item {
  flex: 1 0 auto;
}

#menu {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-around;
}

#menu article {
	flex: 0 1 auto;
}

.flex-grow {
	flex: 1 0 auto;
}
</style>
