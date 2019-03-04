import { Ingredient } from './ingredient';
import { Step } from './step';

export class Brew {

    public id?: number;
    public beginning?: Date;
    public end?: Date;
    public model?: string;
    public malts?: Ingredient[];
    public hops?: Ingredient[];
    public yeasts?: Ingredient[];
    public spices?: Ingredient[];
    public steps?: Step[];
    public state?: string;
    public title?: string;

}