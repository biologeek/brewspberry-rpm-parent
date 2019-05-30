import { Component, OnInit, Input } from '@angular/core';
import { StepService } from '../../../services/step.service';
import { Observable } from 'rxjs';
import { StepStage } from '../../../beans/brewery/step-stage';
import { Step } from '../../../beans/brewery/step';

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
  
  
  constructor(private stepService: StepService) {
    this.currentOp = new StepStage();
    this.currentOp.beginning = 0;
    this.currentOp.duration = 120;
    this.currentStep = {plannedDuration: {quantity: 15, unit: 'MINUTES'}, stages: []};
    
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
