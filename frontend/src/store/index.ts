import Vue from "vue";
import Vuex, { StoreOptions } from "vuex";
import VuexPersistence from 'vuex-persist'

import { UserModule, UserState } from "./user.module";
import { MenuModule, MenuState } from "./menu.module";
import { OrderModule, OrderState } from "./order.module";

Vue.use(Vuex);

export interface RootState {
  user: UserState;
  menu: MenuState;
  order: OrderState;
}

const vuexLocal = new VuexPersistence<RootState>({
  storage: window.localStorage,
  //reducer: (state) => ({ navigation: state.user }),
  modules: ['user'],
  //filter: (mutation) => mutation.type == 'addNavItem'
})

const store: StoreOptions<RootState> = {
  modules: {
    user: UserModule,
    menu: MenuModule,
    order: OrderModule,
  },
  plugins: [vuexLocal.plugin],
};

export default new Vuex.Store<RootState>(store);
