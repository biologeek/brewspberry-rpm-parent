import { Component, OnInit, Inject } from '@angular/core';
import { StepStage } from '../beans/brewery/step-stage';
import { Quantity } from '../beans/quantity';
import { UnitService } from '../services/unit.service';
import { StepService } from '../services/step.service';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialogRef } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-stage-dialog',
  templateUrl: './stage-dialog.component.html',
  styleUrls: ['./stage-dialog.component.css']
})
export class StageDialogComponent implements OnInit {


  private newStage: StepStage = {type: 'CONSTANT'};
  duration: Quantity = {unit: 'MINUTES'};
  timeToStep: Quantity = {unit: 'MINUTES'};
  private timeUnits = [
    'MILLISECONDS', 'SECONDS', 'MINUTES', 'HOURS', 'DAYS'
  ]
  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogInputData: any
    , private dialogRef: MatDialogRef<StageDialogComponent>
    , private unitService: UnitService
    , private stepService: StepService
    , private snackbar: MatSnackBar) { }

  ngOnInit() {
  }


  beginningToStepCalculation() {
    this.newStage.beginningToStep = this.unitService.convertTo(this.timeToStep, 'MILLISECONDS').quantity;
  }

  onSubmit(){
    this.beginningToStepCalculation();
    this.stepService.pushNewStage(this.dialogInputData.step, this.newStage).subscribe(res => {
      this.snackbar.open("Stage saved !", null, {
        duration: 3000,
        panelClass: ['mat-snack-bar-ok']
      });
      this.dialogRef.close(res);
    }, (err: HttpErrorResponse) => {
      this.snackbar.open("Could not save stage !", null, {
        duration: 3000,
        panelClass: ['mat-snack-bar-error']
      });
    });
  }

}
