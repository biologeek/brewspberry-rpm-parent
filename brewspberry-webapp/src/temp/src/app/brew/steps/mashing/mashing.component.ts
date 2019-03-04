import { Component, OnInit, Input } from '@angular/core';
import { StepService } from '../../../services/step.service';
import { Observable } from 'rxjs';
import { StepStage } from '../../../beans/step-stage';
import { Step } from '../../../beans/step';
import { Options } from 'ng5-slider';

@Component({
  selector: 'app-mashing',
  templateUrl: './mashing.component.html',
  styleUrls: ['./mashing.component.css']
})
export class MashingComponent implements OnInit {
  
  @Input()
  step: number;
  
  currentStep: Step;
  currentOp: StepStage;
  
  sliderOptions: Options = {};
  
  constructor(private stepService: StepService) {
    this.currentOp = new StepStage();
    this.currentOp.beginTime = 0;
    this.currentOp.endTime = 120;
    this.currentStep = {plannedDuration: {quantity: 15, unit: 'MINUTES'}, stages: []};
    
    this.sliderOptions = {
      floor: 0,
      ceil: this.currentStep.plannedDuration.quantity,
      step: 1
    };
  }
  
  ngOnInit() {
    this.stepService.getStagesForStep(this.step).subscribe(data => {
      this.currentStep.stages = data;
      console.log(this.currentStep.stages);
      
    });
  }
  
  
  pushNewStage() {
    this.stepService.pushNewStage(this.currentStep, this.currentOp).subscribe(data => {
      this.currentStep.stages = data;
      this.currentOp = new StepStage();
      console.log(this.currentStep.stages);
    });
    
  }
  
}
