import { Module } from 'vuex';
import Api from '@/api';
import { RootState } from '.';

export interface UserState {
  token?: string;
}

const namespaced: boolean = true;

export const UserModule: Module<UserState, RootState> = {
  namespaced,
  state: {
    token: undefined,
  },
  getters: {
    isLoggedIn(state): boolean {
      return !!state.token;
    },
    getClaims(state): string | undefined {
      if (!state.token) {
        console.error('no token');
        return;
      }
      
      const parts = state.token.split('.');
      if (parts.length != 3) {
        console.error('invalid token');
        return;
      }

      try {
        const data = atob(parts[1]);
        const claims = JSON.parse(data);

        return claims;
      } catch(e) {
        
        console.error('invalid token', e);
        return;
      }
    },
    getLogin(state, getters): string | undefined {
      const claims = getters.getClaims;
      
      if (!claims) {
        console.error('no claims');
        return;
      }
      
      return claims.sub;
    },
    getRole(state, getters): string | undefined {
      const claims = getters.getClaims;
      
      if (!claims) {
        console.error('no claims');
        return;
      }
      
      return claims.roles[0];
    },
    isAdmin(state, getters): boolean {
      const role = getters.getRole;
      
      if (!role) {
        console.error('no claims');
        return false;
      }
      
      return role === 'ROLE_ADMIN';
    },
  },
  mutations: {

    setToken(state, token) {
      state.token = token;
    },

    removeToken(state) {
      state.token = undefined;
    },
  },
  actions: {
    async loginUser(context, { username, password }) {
      try {
        const token = await Api.loginUser(username, password)
        context.commit("setToken",token) 
      } catch(error) {
        context.commit("removeToken")

        throw error;
      }
    },

    logout(context) {
      //context.commit("setUser", null)
      context.commit("removeToken")
      //TokenService.removeToken()
    },
  },
};