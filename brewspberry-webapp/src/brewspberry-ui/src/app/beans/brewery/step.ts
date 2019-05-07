import { StepStage } from "./step-stage";
import { Quantity } from "../quantity";
import { StepIngredient } from './step-ingredient';

export class Step {
    public id?;
    public name?;
    public type?;
    public plannedDuration?: Quantity;
    effectiveDuration?: Quantity;
    public beginning: number;
    public end: number;
    public position: number;
    stages?: StepStage[];
    ingredients: StepIngredient[];
}