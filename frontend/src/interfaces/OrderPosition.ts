import Pizza from './Pizza';

export default interface OrderPosition {
	id: number,
	amount: number,
	pizzaType: Pizza,
	dough: string,
	size: string,
	price: number
}