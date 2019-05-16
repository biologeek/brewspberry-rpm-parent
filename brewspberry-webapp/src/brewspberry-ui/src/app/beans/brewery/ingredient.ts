import { Quantity } from '../quantity';


export class Ingredient {
	public id?: number;
	public type?: string;
	public model?: string;
	public brand?: string; 
	public quantity?: Quantity = {quantity: 0, unit: ''};
}