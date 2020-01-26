import Vue from "vue";

import { ValidationProvider, ValidationObserver, extend, localize, setInteractionMode } from "vee-validate";
import { required, min, max, email, regex } from "vee-validate/dist/rules";
import pl from "vee-validate/dist/locale/pl.json";


export default function configureValidation() {
  extend("required", required);
  extend("min", min);
  extend("max", max);
  extend("email", email);
  extend("regex", regex);
  extend('password2', {
    params: ['target'],
    validate(value, params: Record<string, any>) {
      const target = params.target
      return value === target;
    },
    message: 'Hasła się nie zgadzają'
  });

  localize('pl', pl);

  setInteractionMode('eager');

  Vue.component("ValidationProvider", ValidationProvider);
  Vue.component("ValidationObserver", ValidationObserver);
}
