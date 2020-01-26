<template>
  <header>
    <nav>
      <router-link to="/">
        <h2 class="logo">Pizzeria</h2>
      </router-link>
    </nav>
    <nav>
      <router-link to="/login" tag="button" v-if="!isLoggedIn">Zaloguj</router-link>
      <router-link to="/register" tag="button" v-if="!isLoggedIn">Załóż konto</router-link>
      
      <router-link to="/order" tag="button" v-if="isLoggedIn">Zamówienie</router-link>
      <div v-if="isLoggedIn" class="dropdown">
        <button class="dropbtn">{{ getLogin }}</button>
        <div class="dropdown-content">
          <router-link to="/account">Konto</router-link>
          <router-link to="/admin/menu" v-if="isAdmin">Edytuj Menu</router-link>
          <a href="#" @click="logout()">Wyloguj</a>
        </div>
      </div> 
    </nav>
  </header>
</template>
<script lang="ts">
import Vue from "vue";
import { mapState, mapGetters, mapActions } from 'vuex'

export default Vue.extend({
  computed: {
		...mapGetters('user', ['isLoggedIn', 'getLogin', 'isAdmin']),
  },
  methods: {
    ...mapActions('user', ['logout']),
  }
});
</script>
<style lang="scss" scoped>
.logo {
  color: #080;
  font-style: italic;
}

nav a {
  margin: 5px;
}

nav button {
  width: 150px;
  margin: 5px;
  background-color: #080;
  color: white;
  padding: 5px;
  font-size: 1.2em;
  border: none;
  cursor: pointer;
}

.dropdown {
  position: relative;
  display: inline-block;

  &:hover .dropdown-content {
    display: block;
  }
  
  &:hover .dropbtn {
    background-color: #060;
  }
}

/* Dropdown Content (Hidden by Default) */
.dropdown-content {
  display: none;
  position: absolute;
  background-color: #eee;
  width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  
  a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
  }

  a:hover { 
    background-color: #ddd;
  }
}




header.main {
  display: flex;
  justify-content: space-between;
}
</style>
