import { Component, OnInit, Inject } from '@angular/core';
import { UnitService } from '../services/unit.service';
import { StepIngredient } from '../beans/brewery/step-ingredient';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { Ingredient } from '../beans/brewery/ingredient';
import { Quantity } from '../beans/quantity';
import { StepService } from '../services/step.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-ingredients-dialog',
  templateUrl: './ingredients-dialog.component.html',
  styleUrls: ['./ingredients-dialog.component.css']
})
export class IngredientsDialogComponent implements OnInit {


  ingredients: Ingredient[];
  units: any[];
  additionTime: Quantity = {quantity: 0, unit: 'MINUTES'};
  timeUnits: any[];
  newIngredient: StepIngredient = {quantityAdded: {}};

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogInputData: any
    , private dialogRef: MatDialogRef<IngredientsDialogComponent>
    , private unitService: UnitService
    , private stepService: StepService
    , private snackbar: MatSnackBar) { }

  ngOnInit() {
    this.ingredients = this.dialogInputData.ingredients;
    this.units = this.unitService.units;
    this.timeUnits = this.unitService.units[3].units;
  }

  setAdditionTime() {
    console.log('Converting : ' + this.additionTime + ' to ' + this.unitService.convertTo(this.additionTime, 'MILLISECONDS').quantity);
    this.newIngredient.additionTime = this.unitService.convertTo(this.additionTime, 'MILLISECONDS').quantity;
  }

  onSubmit(){
    this.setAdditionTime();
    this.stepService.pushNewIngredient(this.dialogInputData.step.id, this.newIngredient).subscribe(res => {
      this.snackbar.open("Device saved !", null, {
        duration: 3000,
        panelClass: ['mat-snack-bar-ok']
      });
      this.dialogRef.close(res);
    }, (err: HttpErrorResponse) => {
      this.snackbar.open("Could not save device !", null, {
        duration: 3000,
        panelClass: ['mat-snack-bar-error']
      });
    });
  }

}
