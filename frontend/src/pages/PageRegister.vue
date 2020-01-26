<template>
  <section class="thin-page">
    <header>
      <h2>Załóż konto</h2>
    </header>
    <ValidationObserver ref="observer" v-slot="{ handleSubmit, errors }">
      <form @submit.prevent="handleSubmit(saveData); submitted=true;" novalidate>

        <validation-provider name="Login" v-slot="{ errors }" class="row">
          <label for="login" class="required">Login:</label>
          <input
            type="text"
            id="login"
            v-model="user.login"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz Login"
            required
            minlength="5"
            maxlength="15">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="Hasło" v-slot="{ errors }" class="row">
          <label for="password" class="required">Hasło:</label>
          <input
            type="password"
            id="password"
            v-model="user.password"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz Hasło"
            required
            minlength="8"
            maxlength="20">
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="Powtórz Hasło" rules="password2:@Hasło" v-slot="{ errors }" class="row">
          <label for="password2" class="required">Powtórz Hasło:</label>
          <input
            type="password"
            id="password2"
            v-model="user.password2"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Powtórz Hasło"
            required>
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>
        
        <validation-provider name="Imię" v-slot="{ errors }" class="row">
          <label for="name" class="required">Imię:</label>
          <input
            type="text"
            id="name"
            v-model="user.name"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz Imię"
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
            placeholder="Wpisz Nazwisko"
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
            placeholder="Wpisz E-mail"
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

        <button id="login-btn">Załóż konto</button>
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
        pass: '',
        name: '',
        surname: '',
        tel: '',
        email: '',
      }
    };
  },
  methods: {
    saveData: async function() {
      try {
        await Api.register(this.user);

        this.$router.push('/')
      } catch(error) {
        console.log(error)
        const observer = this.$refs.observer as InstanceType<typeof ValidationObserver>;
        let msg = error.message || 'Błąd rejestracji'
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
    grid-template-columns: 30% 1fr;
  }
}
</style>