<template>
  <div id="app">
    <app-header class="main"></app-header>
    <section class="main">
      <router-view/>
    </section>
    <app-footer class="main"></app-footer>
  </div>
</template>
<script lang="ts">
import Vue from 'vue'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
export default Vue.extend({
  components: {
    AppHeader,
    AppFooter
  }
})
</script>
<style lang="scss">
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

#app {
	font-family: Arial, Helvetica, sans-serif;
	background-color: #eee;
	color: #000;
}

.main {
	margin: 10px;
	padding: 10px;
	background-color: #ddd;
}
</style>
