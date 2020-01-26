import PizzaPrice from './PizzaPrice';

export default interface Pizza     {
	id: number,
	name: string,
	description: string,
	isVegetarian: boolean,
	isVegan: boolean,
	isSpicy: boolean,
	basePrice: number,
	image: any
	pizzaPrices?: PizzaPrice[]
}