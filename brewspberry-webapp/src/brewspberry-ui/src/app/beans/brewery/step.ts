import { StepStage } from "./step-stage";
import { Quantity } from "../quantity";

export class Step {
    public id?;
    public name?;
    public type?;
    public plannedDuration?: Quantity;
    effectiveDuration?: Quantity;
    stages?: StepStage[];
}