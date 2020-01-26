import { Module } from 'vuex';
import { RootState } from '.';
import OrderPosition from '@/interfaces/OrderPosition';

export interface OrderState {
  orderPositions: OrderPosition[]
}

const namespaced: boolean = true;

export const OrderModule: Module<OrderState, RootState> = {
  namespaced,
  state: {
    orderPositions: []
  },
  getters: {
  },
  mutations: {
    addPizzaOrder(state, pizza: any) {
      state.orderPositions.push(pizza);
    },

    editPizzaOrder(state, pizza: any) {
      const idx = state.orderPositions.indexOf(pizza);
      state.orderPositions.splice(idx, 1, pizza);
    },

    removePizzaOrder(state, pizza: any) {
      const idx = state.orderPositions.indexOf(pizza);
      state.orderPositions.splice(idx, 1);
    },

    resetOrder(state) {
      state.orderPositions = [];
    }
  },
  actions: {
  },
};