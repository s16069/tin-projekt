<template>
  <section class="thin-page">
    <header>
      <h2>Zaloguj się</h2>
    </header>
    <ValidationObserver ref="observer" v-slot="{ handleSubmit, errors }">
      <form @submit.prevent="handleSubmit(loginUser); submitted=true;" novalidate>
        <validation-provider name="Login" v-slot="{ errors }" class="row">
          <label for="login" class="required">Login:</label>
          <input
            type="text"
            id="login"
            v-model="username"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz login"
            required
            minlength="5"
            maxlength="15"
          />
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <validation-provider name="Hasło" v-slot="{ errors }" class="row">
          <label for="password" class="required">Hasło:</label>
          <input
            type="password"
            id="password"
            v-model="password"
            v-bind:class="{ invalid: errors.length > 0 }"
            placeholder="Wpisz hasło"
            required
          />
          <span class="error">{{ errors[0] }}</span>
        </validation-provider>

        <button id="login-btn">Zaloguj</button>
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
import { BadCredentialsError } from '../api';

export default Vue.extend({
  data: function() {
    return {
      submitted: false,
      username: "",
      password: "",
    };
  },
  mounted() {
   
  },
  methods: {
    loginUser: async function() {
      try {
        await this.$store.dispatch("user/loginUser", {
          username: this.username,
          password: this.password
        });

        this.$router.push('/')
      } catch(error) {
        console.log(error)
        const observer = this.$refs.observer as InstanceType<typeof ValidationObserver>;
        let msg = (error instanceof BadCredentialsError) ? "Zły login lub hasło" : "Błąd logowania"
        observer.setErrors({
          'Login': [ msg ]
        });
      }
    }
  }
});
</script>

<style lang="scss" scoped>

</style>