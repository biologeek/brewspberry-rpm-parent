import { Quantity } from '../quantity';
import { Ingredient } from './ingredient';

export class StepIngredient {
    ingredient?: number;
    additionTime?: number;
    quantityAdded?: Quantity = {};
}