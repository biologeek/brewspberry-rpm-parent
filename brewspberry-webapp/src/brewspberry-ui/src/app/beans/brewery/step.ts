import { StepStage } from "./step-stage";
import { Quantity } from "../quantity";
import { StepIngredient } from './step-ingredient';

export class Step {
    public id?;
    public name?;
    public type?;
    public plannedDuration?: Quantity = {};
    public effectiveDuration?: Quantity = {};
    public beginning?: number | Date;
    public end?: number | Date;
    public position?: number;
    stages?: StepStage[];
    ingredients?: StepIngredient[];
}