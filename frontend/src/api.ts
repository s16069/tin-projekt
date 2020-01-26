
import axios from 'axios'

import store from './store'

import User, { UserBase, UserData } from './interfaces/User';
import Pizza from './interfaces/Pizza';
import Order from './interfaces/Order';

//import TokenService from './services/token.service'

const AUTHORIZATION_HEADER = "authorization"

const api = axios.create({
	baseURL: 'http://127.0.0.1:9000/',
});

api.interceptors.request.use(function (config) {
	config.headers[AUTHORIZATION_HEADER] = store.state.user.token;

	return config;
});

api.interceptors.response.use(function (response) {
	return response;
}, function (error) {
	if (error.response && error.response.status === 403) {
		console.log('Unauthorized', error);
		
		store.dispatch('user/logout');
	}
	return Promise.reject(error);
});


export class BadCredentialsError extends Error {
	constructor(m: string) {
		super(m);

		// Set the prototype explicitly.
		Object.setPrototypeOf(this, BadCredentialsError.prototype);
	}
}

export default class Api {
	static async loginUser(username: string, password: string): Promise<string> {
		const params = new URLSearchParams();
		params.append('username', username);
		params.append('password', password);

		try {
			const response = await api.post('/login', params)

			return response.headers[AUTHORIZATION_HEADER]
		} catch(error) {
			if (error.response && error.response.status === 403) {
				throw new BadCredentialsError(error.message)
			} else {
				throw error;
			}
		}
	}

	static async register(user: UserBase): Promise<User> {
		const response = await api.post('/register', user)

		return response.data
	}

	static async getUserData(): Promise<User> {
		const response = await api.get('/users/me')

		return response.data
	}

	static async saveUserData(user: UserData): Promise<User> {
		const response = await api.post('/users/me', user)

		return response.data
	}

	static async listPizzas(): Promise<Pizza> {
		const response = await api.get('/menu/pizzas')

		return response.data
	}

	static async listSizes(): Promise<String[]> {
		const response = await api.get('/menu/sizes')

		return response.data
	}

	static async listDoughs(): Promise<String[]> {
		const response = await api.get('/menu/doughs')

		return response.data
	}

	static async getPizza(pizzaId: number): Promise<Pizza> {
		const response = await api.get(`/menu/pizzas/${pizzaId}`)

		return response.data
	}

	static async updatePizza(pizzaId: number, pizza: Pizza): Promise<Pizza> {
		const response = await api.put(`/admin/pizzas/${pizzaId}`, pizza)

		return response.data
	}

	static async savePizza(pizza: Pizza): Promise<Pizza> {
		const response = await api.post(`/admin/pizzas`, pizza)

		return response.data
	}
	
	static async deletePizza(pizzaId: number): Promise<Pizza> {
		const response = await api.delete(`/admin/pizzas/${pizzaId}`)

		return response.data
	}

	static async addOrder(pizzas: any[], address: any): Promise<Order> {
		const response = await api.post('/orders', {
			pizzas: pizzas.map(p => ({
				amount: p.amount,
				size: p.size,
				dough: p.dough,
				pizzaType: {
					id: p.pizzaType.id
				}
			})),
			address
		})

		return response.data
	}
}
