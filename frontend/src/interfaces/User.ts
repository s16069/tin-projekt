import Address from './Address';

export interface UserBase {
  login: string,
  name: string,
  surname: string,
  tel: string,
  email: string,
}

export interface UserData extends UserBase {
  address: Address,
}

export default interface User extends UserData {
  id: number,
  userRole: "ROLE_ADMIN" | "ROLE_EMPLOYEE" | "ROLE_CLIENT",
}