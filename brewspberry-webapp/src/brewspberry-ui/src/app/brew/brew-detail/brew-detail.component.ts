import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Step } from 'src/app/beans/brewery/step';
import { Brew } from 'src/app/beans/brewery/brew';
import { BrewService } from 'src/app/services/brew.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { IngredientService } from 'src/app/services/ingredient.service';
import { Ingredient } from 'src/app/beans/brewery/ingredient';

import * as d3 from 'd3-selection';
import * as d3Scale from 'd3-scale';
import * as d3Axis from 'd3-axis';
import * as d3Shape from 'd3-shape';
import * as d3Time from 'd3-time-format';
import * as d3Format from 'd3-format';
import { StepService } from 'src/app/services/step.service';
import { StepStage } from 'src/app/beans/brewery/step-stage';
import { MatDialogRef, MatDialog } from '@angular/material';
import { IngredientsDialogComponent } from 'src/app/ingredients-dialog/ingredients-dialog.component';
import { StageDialogComponent } from 'src/app/stage-dialog/stage-dialog.component';


@Component({
  selector: 'app-brew-detail',
  templateUrl: './brew-detail.component.html',
  styleUrls: ['./brew-detail.component.css']
})
export class BrewDetailComponent implements OnInit {

  @ViewChild('stagesContainer')
  private stagesRef: ElementRef;

  private padding = 20;

  private stageTemporalUnit;

  currentStep: Step = new Step();
  brew: Brew;
  ingredientsList: Ingredient[];
  brewId: number;

  subs$: Subscription[] = [];

  ingredientsDialogRef: MatDialogRef<any, any>;
  stagesDialogRef: MatDialogRef<any, any>;

  constructor(private route: ActivatedRoute
    , private brewService: BrewService
    , private ingredientService: IngredientService
    , private stepService: StepService
    , private router: Router
    , private ingredientsDialog: MatDialog
    , private stagesDialog: MatDialog) { }

  ngOnInit() {
    this.brew = new Brew();
    this.subs$.push(this.route.params.subscribe(prm => {
      this.brewId = prm['id'];
      /*
       * Getting all ingredients
       */
      this.subs$.push(this.ingredientService.getAllIngredients().subscribe(res => {
        console.log('Got ' + res.map(s => JSON.stringify(s)));
        this.ingredientsList = res;
      }));
      /*
       * Getting brew
       */
      const brewSub$ = this.brewService.getBrew(this.brewId).subscribe(brw => {
        this.stepService.calculateStageDates(brw.steps);
        this.brew = brw;
        this.dispatchBrewQuantities();
      });
      this.subs$.push(brewSub$);
    }));
  }


  private routeToEdit() {

  }

  private activateStep(step: Step) {
    this.currentStep = step;
    console.log(this.currentStep);
    setTimeout(() => this.initStagesChart(), 200);
  }

  private dispatchBrewQuantities() {
    const brewQuantities = this.calculateBrewQuantities();
    for (let bq of brewQuantities) {
      if (this.ingredientsList) {
        const possibleIngredient = this.ingredientsList.filter(ing => ing.id === bq.id)[0]
        if (possibleIngredient) {
          possibleIngredient.quantity = bq.quantity;
        }
      }
    }
  }
  
  getIngredientById(ingredientId): Ingredient {
    const poss = this.ingredientsList.filter(s => s.id === ingredientId);
    if (poss && poss.length === 1) {
      console.log(poss[0]);
      return poss[0];
    } else {
      console.log('No Ingredient ' + ingredientId);
      return new Ingredient();
    }
  }

  /**
   * Sums up step quantities for every ingredient and eturns global brew quantities 
   */
  private calculateBrewQuantities() {
    const finalQuantities = [];
    for (let step of this.brew.steps) {
      for (let ing of step.ingredients) {
        const possible = finalQuantities.filter(s => s.id === ing.ingredient);
        if (possible.length == 1) {
          possible[0].quantity.quantity += ing.quantityAdded.quantity;
        } else {
          finalQuantities.push({
            id: ing.ingredient,
            quantity: ing.quantityAdded
          });
        }
      }
    }
    return finalQuantities;
  }


  /**
   * Returns an array containing chart bounds in millis for X axis and Celsius for temperatures
   */
  private getMinAndMaxForStep(): any {
    const res = { x: [0, 0], y: [0, 0] };
    for (let stage of this.currentStep.stages) {
      if (stage.beginningToStep < res.x[0]) {
        res.x[0] = stage.beginningToStep;
      }
      if (stage.beginningToStep + stage.duration * 1000 > res.x[1]) {
        res.x[1] = stage.beginningToStep + stage.duration * 1000;
      }
      if (Math.min(stage.startTemperature, stage.endTemperature) < res.y[0]) {
        res.y[0] = Math.min(stage.startTemperature, stage.endTemperature);
      }
      if (Math.max(stage.startTemperature, stage.endTemperature) > res.y[1]) {
        res.y[1] = Math.max(stage.startTemperature, stage.endTemperature);
      }
    }

    res.x[0] = this.toBestTemporalUnit(res.x[0], res.x);
    res.x[1] = this.toBestTemporalUnit(res.x[1], res.x);
    return res;
  }

  private initStagesChart() {
    const data = this.convertToChartData();
    const chart = d3.select('#stages-chart');

    const minAndMax = this.getMinAndMaxForStep();

    const mainG = chart.append('g')
      .attr('class', 'main-g')
      .attr('width', this.stagesRef.nativeElement.clientWidth - this.padding * 2)
      .attr('height', this.stagesRef.nativeElement.clientHeight - this.padding * 2)
      .attr('transform', `translate(${this.padding}, ${this.padding})`);


    const x = d3Scale.scaleLinear()//
      .range([0, this.stagesRef.nativeElement.clientWidth - this.padding * 2])
      .domain(minAndMax.x);

    const y = d3Scale.scaleLinear()//
      .range([0, this.stagesRef.nativeElement.clientHeight - this.padding * 2])
      .domain([minAndMax.y[1], minAndMax.y[0]]);

    const line = d3Shape//
      .line()//
      .x((item: any) => {
        console.log('X : ' + this.toBestTemporalUnit(item.x, minAndMax.x));
        return x(this.toBestTemporalUnit(item.x, minAndMax.x));
      })
      .y((item: any) => {
        console.log('Y : ' + y(item.y));
        return y(item.y);
      });

    const xAxis = d3Axis//
      .axisBottom(x)//
      .ticks(3);
    const yAxis = d3Axis//
      .axisLeft(y)//
      .ticks(6);

    mainG.append('g')
      .attr('class', 'axis x-axis')
      .attr('transform', `translate(0, ${this.stagesRef.nativeElement.clientHeight - this.padding * 2})`)
      .call(xAxis);

    mainG.append('g')
      .attr('class', 'axis y-axis')
      .call(yAxis);

    mainG.append('path')
      .attr('width', `${this.stagesRef.nativeElement.clientWidth - this.padding * 2}`)
      .attr('height', `${this.stagesRef.nativeElement.clientHeight - this.padding * 2}`)
      .datum(data)
      .attr('class', 'line')
      .attr('d', line)
      .attr('stroke', 'steelblue')
      .attr('fill', 'transparent');
  }


  private convertToChartData(): any {
    const res = [];

    for (let elt of this.currentStep.stages) {
      res.push({
        x: elt.beginningToStep,
        y: elt.startTemperature
      });
      res.push({
        x: elt.beginningToStep + elt.duration * 1000,
        y: elt.endTemperature
      });
    }
    return res;
  }

  /**
   * Converts to best unit of time
   * @param time time in ms
   * @param bounds bounds in ms
   */
  private toBestTemporalUnit(time: number, bounds: number[]): number {
    const diff = bounds[1] - bounds[0];

    // In case min and max are over 100 minutes, convert to hours
    if (diff > 60000000) {
      bounds = bounds.map(s => s / 1000 / 3600);
      this.stageTemporalUnit = 'h';
      // console.log(time / 1000 / 3600 + 'h');
      return time / 1000 / 3600;
    } else {
      this.stageTemporalUnit = 'min';
      bounds = bounds.map(s => s / 1000 / 60);
      // console.log(time / 1000 / 60 + 'min');
      return time / 1000 / 60;
    }
  }

  /**
   * Opens a popup to add an ingredient
   */
  private openIngredientsPopup() {
    this.ingredientsDialogRef = this.ingredientsDialog.open(IngredientsDialogComponent, {
      width: '50%',
      data: {
        step: this.currentStep,
        ingredients: this.ingredientsList
      }/*,
      height: '500px'*/
    });
    this.ingredientsDialogRef.afterClosed().subscribe(() => {
      this.stepService.getStep(this.currentStep.id).subscribe(res => {
        this.currentStep = res;
      });
    });
  }

  /**
   * Opens a popup to add a stage
   */
  private openStagesPopup() {
    this.stagesDialogRef = this.stagesDialog.open(StageDialogComponent, {
      width: '50%',
      data: {
        step: this.currentStep
      }/*,
      height: '500px'*/
    });
    this.stagesDialogRef.afterClosed().subscribe(() => {
      this.stepService.getStep(this.currentStep.id).subscribe(res => {
        this.currentStep = res;
      });
    });
  }

}
