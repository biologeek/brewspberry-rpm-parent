import { Ingredient } from './ingredient';
import { Step } from './step';

export class Brew {

	public id?: number;
	public model?: string;
	public malts?: Ingredient[];
	public hops?: Ingredient[];
	public yeasts?: Ingredient[];
	public spices?: Ingredient[];
	public steps?: Step[];

}