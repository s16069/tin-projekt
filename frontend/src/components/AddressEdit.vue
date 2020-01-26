<template>
<b-modal v-model="show" title="Adres doręczenia" @ok="customized" ok-only no-close-on-esc no-close-on-backdrop hide-header-close>
	<b-alert :show="fieldsRequired" variant="danger">Uzupełnij dane</b-alert>
	<b-container>
		<b-row class="margin">
			<b-col sm="3">
				<label>Miasto</label>
			</b-col>
			<b-col sm="9">
				<b-form-input type="text" v-model="city" :disabled="!edit"></b-form-input>
			</b-col>
		</b-row>
		<b-row class="margin">
			<b-col sm="3">
				<label>Ulica</label>
			</b-col>
			<b-col sm="9">
				<b-form-input type="text" v-model="street" :disabled="!edit"></b-form-input>
			</b-col>
		</b-row>
		<b-row class="margin">
			<b-col sm="3">
				<label>Nr domu</label>
			</b-col>
			<b-col sm="9">
				<b-form-input type="text" v-model="homeNr" :disabled="!edit"></b-form-input>
			</b-col>
		</b-row>
		<b-row class="margin">
			<b-col sm="3">
				<label>Nr mieszkania</label>
			</b-col>
			<b-col sm="9">
				<b-form-input type="text" v-model="flatNr" :disabled="!edit"></b-form-input>
			</b-col>
		</b-row>
		<b-row class="margin">
			<b-col sm="3">
				<b-button @click="editClicked">Edytuj</b-button>
			</b-col>
		</b-row>
	</b-container>
</b-modal>
</template>

<script>
export default {
	name: 'AddressEdit',
	mounted: function() {
		this.show = true
	},
	data: function() {
		return {
			show: true,
			fieldsRequired: false,
			edit: false,
			city: this.initAddress.city,
			street: this.initAddress.street,
			homeNr: this.initAddress.homeNr,
			flatNr: this.initAddress.flatNr,
		}
	},
	props: ['initAddress'],
	computed: {

	},
	methods: {
		editClicked: function() {
			this.edit = true
		},
		customized: function(ev) {
			if(!this.city || !this.street || !this.homeNr) {
				this.fieldsRequired = true
				ev.preventDefault()
				return;
			}

			const address = this.edit ? {
				city: this.city,
				street: this.street,
				homeNr: this.homeNr,
				flatNr: this.flatNr,
			} : this.initAddress;

			this.$emit('addressCustomized', address)
		},
	}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.name {
	font-weight: bold;
}
.margin {
	margin: 5px 0;
}
</style>
