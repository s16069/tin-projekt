<template>
  <footer>
    <nav>
      <span>Jesteś tu:</span>
      <a href="index.html">Strona główna</a>
    </nav>
    <nav>
      <a href="contact.html">Kontakt</a>
      <a href="privacy.html">Polityka prywatności</a>
    </nav>
  </footer>
</template>
<script lang="ts">
import Vue from "vue";
export default Vue.extend({});
</script>
<style lang="scss" scoped>

footer {
	display: flex;
	justify-content: space-between;
}

nav a {
	margin: 5px
}

nav.pager {
	text-align: center;
	margin: 10px 0px;
}

</style>
