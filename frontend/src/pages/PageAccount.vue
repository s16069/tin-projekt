<template>
  <section class="thin-page">
    <header>
      <h2>Konto</h2>
    </header>
    <ValidationObserver ref="observer" v-slot="{ handleSubmit, errors }">
      <form @submit.prevent="handleSubmit(saveData); submitted=true;" novalidate>

        <h4>Adres:</h4>
      
        <validation-provider name="Miejscowość" v-slot="{ errors }" class="row">
          <label for="city">Miejscowość:</label>
          <select id="city" v-model="user.address.city" v-bind:class="{ invalid: errors.length > 0 }">
            <option selected disabled>Wybierz miejscowość</option>
            <option>Warszawa</option>
            <option>Łomianki</option>
          </select>
        </validation-provider>
        
        <validation-provider name="Ulica" v-slot="{ errors }" class="row">
          <label for="street">Ulica:</label>
          <input
            type="text"
            id="street"
            v-model="user.address.street"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz ulicę"
            maxlength="50">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
        
        <validation-provider name="Nr domu" v-slot="{ errors }" class="row">
          <label for="homeNr">Nr domu:</label>
          <input
            type="text"
            id="homeNr"
            v-model="user.address.homeNr"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz nr domu"
            maxlength="10">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
        
        <validation-provider name="Nr mieszkania" v-slot="{ errors }" class="row">
          <label for="flatNr">Nr mieszkania:</label>
          <input
            type="text"
            id="flatNr"
            v-model="user.address.flatNr"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz nr mieszkania"
            maxlength="10">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
        
        <h4 class="both-columns">Kontakt:</h4>
        
        <validation-provider name="Imię" v-slot="{ errors }" class="row">
          <label for="name" class="required">Imię:</label>
          <input
            type="text"
            id="name"
            v-model="user.name"
            v-bind:class="{ invalid: errors.length > 0 }"
            required>
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="Nazwisko" v-slot="{ errors }" class="row">
          <label for="name" class="required">Nazwisko:</label>
          <input
            type="text"
            id="surname"
            v-model="user.surname"
            v-bind:class="{ invalid: errors.length > 0 }"
            required>
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="E-mail" v-slot="{ errors }" class="row">
          <label for="email" class="required">E-mail:</label>
          <input
            type="email"
            id="email"
            v-model="user.email"
            v-bind:class="{ invalid: errors.length > 0 }"
            required>
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
        
        <validation-provider name="Nr telefonu" v-slot="{ errors }" class="row">
          <label for="telephone" class="required">Telefon:</label>
          <input
            type="text"
            id="telephone"
            v-model="user.tel"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz nr telefonu"
            required
            maxlength="9"
            pattern="^[0-9]{9}$">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <h4 class="both-columns">Logowanie</h4>
        
        <div class="row">
          <label for="password">Hasło:</label>
          <button>Zmień hasło</button>
        </div>

        <button id="login-btn">Zapisz</button>
        <ul class="errors" v-if="submitted">
          <li v-for="error in Object.values(errors).flat(1)" :key="error">{{error}}</li>
        </ul>

      </form>
    </ValidationObserver>
  </section>
</template>
<script lang="ts">
import Vue from "vue";
import { ValidationObserver } from 'vee-validate'
import Api from '../api';

export default Vue.extend({
  data: function() {
    return {
      submitted: false,
      user: {
        login: '',
        name: '',
        surname: '',
        tel: '',
        email: '',
        address: {
          city: '',
          street: '',
          homeNr: '',
        }
      }
    };
  },
  async mounted() {
    this.user = await Api.getUserData()
    if (!this.user.address) {
      this.user.address = {
        city: '',
        street: '',
        homeNr: '',
      };
    }
  },
  methods: {
    saveData: async function() {
      try {
        await Api.saveUserData(this.user);

        this.$router.push('/')
      } catch(error) {
        console.log(error)
        const observer = this.$refs.observer as InstanceType<typeof ValidationObserver>;
        let msg = error.message || 'Błąd zapisu';
        observer.setErrors({
          'Login': [ msg ]
        });
      }
    }
  }
});
</script>

<style lang="scss" scoped>
form {
  .row {
    grid-template-columns: 25% 1fr;
  }
}
</style>