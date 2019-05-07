import { Component, OnInit } from '@angular/core';
import { Brew } from '../beans/brewery/brew';
import { Step } from '../beans/brewery/step';
import { BrewService } from '../services/brew.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  timerDays: number;
  timerHours: number;
  timerMinutes: number;
  timerSeconds: number;

  triggerDate: Date;


  currentBrew: Brew;
  currentStep: Step;
  nextStep: Step;

  
  constructor(private brewService: BrewService) { }

  ngOnInit() {

    this.brewService.getBurrentBrew().subscribe((brew: Brew) => {
      this.currentBrew = brew;
      this.updateCurrentAndNextStep();
    });

    this.setupTimer();
  }

  private updateCurrentAndNextStep() {

    const steps = this.currentBrew.steps.sort((a: Step, b: Step) => {
      return a.position - b.position;
    });

    let index: number = 0;
    const potentialSteps = steps.filter((step, idx) => {
      if (step.beginning && !step.end) {
        index = idx;
        return true;
      }
      return false;
    });
    if (potentialSteps && potentialSteps.length === 1) {
      this.currentStep = potentialSteps[0];
      this.nextStep = steps.length > index + 1 ? steps[index + 1] : null;
    }
    
  }

  private setupTimer() {

    const second = 1000,
      minute = second * 60,
      hour = minute * 60,
      day = hour * 24;

      this.triggerDate = new Date();
      this.triggerDate.setMonth(5);
    setInterval(() => {
      if (this.triggerDate) {
        const now = new Date().getTime();
        const delay = this.triggerDate.getTime() - now;
        this.timerDays = Math.floor(delay / day);
        this.timerHours = Math.floor((delay % day) / hour);
        this.timerMinutes = Math.floor((delay % hour) / minute);
        this.timerSeconds = Math.floor((delay % minute) / second);
      }
    }, 1000);
  }
}
