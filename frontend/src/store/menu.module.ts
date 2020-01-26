import { Module } from 'vuex';
import Api from '@/api';
import { RootState } from '.';
import Pizza from '@/interfaces/Pizza';

export interface MenuState {
  pizzas: Pizza[];
  sizes: string[];
  doughs: string[];
}

const namespaced: boolean = true;

export const MenuModule: Module<MenuState, RootState> = {
  namespaced,
  state: {
    pizzas: [],
    sizes: [],
    doughs: [],
  },
  getters: {

  },
  mutations: {
    setPizzas(state, pizzas: Pizza[]) {
      state.pizzas = pizzas;
    },

    setSizes(state, sizes: string[]) {
      state.sizes = sizes;
    },

    setDoughs(state, doughs) {
      state.doughs = doughs;
    },
  },
  actions: {
    async listPizzas(context) {
      const pizzas = await Api.listPizzas()

      context.commit("setPizzas", pizzas);
    },

    async listSizes(context) {
      const sizes = await Api.listSizes()

      context.commit("setSizes", sizes);
    },

    async listDoughs(context) {
      const doughs = await Api.listDoughs()

      context.commit("setDoughs", doughs);
    }
  },
};