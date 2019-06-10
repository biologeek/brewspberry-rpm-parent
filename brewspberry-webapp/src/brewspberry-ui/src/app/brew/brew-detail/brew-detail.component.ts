import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
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
import { UnitService } from 'src/app/services/unit.service';


@Component({
  selector: 'app-brew-detail',
  templateUrl: './brew-detail.component.html',
  styleUrls: ['./brew-detail.component.css']
})
export class BrewDetailComponent implements OnInit, OnDestroy {

  @ViewChild('stagesContainer')
  private stagesRef: ElementRef;

  private padding = {top: 20, bottom: 40, left: 40, right: 20};

  currentStep: Step = new Step();
  brew: Brew = {};
  ingredientsList: Ingredient[];
  brewId: number;

  subs$: Subscription[] = [];
  stepTypes: any[];
  stepHeaderEdit: boolean = false;
  brewHeaderEdit = false;

  originalX = [1000000, 0];

  timeUnits = this.unitService.units.filter(s => s.group === 'time')[0].units.map(s => s.name);

  ingredientsDialogRef: MatDialogRef<any, any>;
  stagesDialogRef: MatDialogRef<any, any>;

  constructor(private route: ActivatedRoute
    , private brewService: BrewService
    , private ingredientService: IngredientService
    , private stepService: StepService
    , private unitService: UnitService
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
        //  console.log('Got ' + res.map(s => JSON.stringify(s)));
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

  ngOnDestroy(){
    this.subs$.forEach(s => {
      s.unsubscribe();
    });
  }

  private cancelBrewHeader(){
    const brewSub$ = this.brewService.getBrew(this.brewId).subscribe(brw => {
      this.stepService.calculateStageDates(brw.steps);
      this.brew = brw;
      this.dispatchBrewQuantities();
      this.brewHeaderEdit = false;
    });
    this.subs$.push(brewSub$);
  }

  private routeToEdit(){
    this.brewHeaderEdit = true; 
  }
  private editHeader() {
    this.stepHeaderEdit = true;
    this.stepService.getAllTypes().subscribe(res => {
      this.stepTypes = res;
    });
  }

  private saveHeader() {
  }

  private cancelHeader() {
    this.stepHeaderEdit = false;
  }

  private activateStep(step: Step) {
    this.currentStep = step;
    //  console.log(this.currentStep);
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
      //     //  console.log(poss[0]);
      return poss[0];
    } else {
      //     //  console.log('No Ingredient ' + ingredientId);
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
    
    const res = { x: [{}, {}], y: [100000000, 0] };
    for (let stage of this.currentStep.stages) {
      if (stage.beginningToStep < this.originalX[0]) {
        this.originalX[0] = stage.beginningToStep;
      }
      if (stage.beginningToStep + stage.duration > this.originalX[1]) {
        this.originalX[1] = stage.beginningToStep + stage.duration;
      }
      if (Math.min(stage.startTemperature, stage.endTemperature) < res.y[0]) {
        res.y[0] = Math.min(stage.startTemperature, stage.endTemperature);
      }
      if (Math.max(stage.startTemperature, stage.endTemperature) > res.y[1]) {
        res.y[1] = Math.max(stage.startTemperature, stage.endTemperature);
      }
    }

    res.x[0] = this.unitService.toBestTemporalUnit(this.originalX[0], this.originalX);
    res.x[1] = this.unitService.toBestTemporalUnit(this.originalX[1], this.originalX);
    return res;
  }

  private initStagesChart() {
    const data = this.convertToChartData();
    const chart = d3.select('#stages-chart');

    const minAndMax = this.getMinAndMaxForStep();

    const mainG = chart.append('g')
      .attr('class', 'main-g')
      .attr('width', this.stagesRef.nativeElement.clientWidth - this.padding.left - this.padding.right)
      .attr('height', this.stagesRef.nativeElement.clientHeight - this.padding.bottom - this.padding.top)
      .attr('transform', `translate(${this.padding.left}, ${this.padding.top})`);


    const x = d3Scale.scaleLinear()//
      .range([0, this.stagesRef.nativeElement.clientWidth - this.padding.top - this.padding.bottom])
      .domain(minAndMax.x.map(s => s.quantity));

    const y = d3Scale.scaleLinear()//
      .range([0, this.stagesRef.nativeElement.clientHeight - this.padding.left - this.padding.right])
      .domain([minAndMax.y[1], minAndMax.y[0]]);

    const line = d3Shape//
      .line()//
      .x((item: any) => {
         // console.log(item.x + ' X : ');
         // console.log(this.unitService.toBestTemporalUnit(item.x, this.originalX));
        return x(this.unitService.toBestTemporalUnit(item.x, this.originalX).quantity);
      })
      .y((item: any) => {
       // console.log(item.y + 'Y : ' + y(item.y));
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
      .attr('transform', `translate(0, ${this.stagesRef.nativeElement.clientHeight - this.padding.top - this.padding.bottom})`)
      .call(xAxis);

    mainG.append('g')
      .attr('class', 'axis y-axis')
      .call(yAxis);

      mainG.append('text')
        .attr('transform', `translate(${(this.stagesRef.nativeElement.clientWidth - this.padding.left - this.padding.right) / 2},
         ${this.stagesRef.nativeElement.clientHeight - this.padding.top})`)
        .style('font-size', '0.8em')
        .style('color', 'black')
        .text('Time in ' + minAndMax.x[0].unit.toLowerCase());


        mainG.append('text')
        .attr('transform', `translate(-${this.padding.left},0)`)
        .style('font-size', '0.8em')
        .style('color', 'black')
        .text('°C');
    
    mainG.append('path')
      .attr('width', `${this.stagesRef.nativeElement.clientWidth - this.padding.left - this.padding.right}`)
      .attr('height', `${this.stagesRef.nativeElement.clientHeight - this.padding.top - this.padding.bottom}`)
      .datum(data)
      .attr('class', 'line')
      .attr('d', line)
      .attr('stroke', 'steelblue')
      .attr('fill', 'transparent');
  }


  private convertToChartData(): any {
    const res = [];

    for (let elt of this.currentStep.stages) {
      //   console.log('Pushing ')
      //   console.log({
      //    x: elt.beginningToStep,
      //    y: elt.startTemperature
      //  });
      //  console.log(' To ');
      //  console.log({
      //   x: elt.beginningToStep + elt.duration,
      //   y: elt.endTemperature
      // });
      // //  console.log(elt);
      res.push({
        x: elt.beginningToStep,
        y: elt.startTemperature
      });
      res.push({
        x: elt.beginningToStep + elt.duration,
        y: elt.endTemperature
      });
    }
    return res;
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
