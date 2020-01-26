<template>
<div>
	<menu-menu :orderEnabled="isLoggedIn"></menu-menu>
</div>
</template>

<script>
import { mapGetters } from 'vuex'

import Menu from './../components/Menu.vue'

export default {
	name: 'PageMain',
	data: function() {
		return {
		}
	},
	props: {
	},
	computed: {
		...mapGetters('user', ['isLoggedIn'])
	},
	methods: {
	},
	components: {
		'menu-menu': Menu,
	}
}
</script>

<style scoped>

</style>
