import Address from './Address';
import OrderPosition from './OrderPosition';
import User from './User';

export default interface Order {
	id: number,
	comments?: string,
  address: Address
  status: "CREATED" | "ACCEPTED" | "PREPARED" | "DELIVERED",
  timeCreated: number,
	timeAccepted?: number,
	timePrepared?: number,
	timeDelivered?: number,
	positions: OrderPosition[],
	client: User,
	price: number
}