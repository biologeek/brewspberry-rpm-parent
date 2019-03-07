import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { BatchRequest } from 'src/app/beans/monitoring/batch-request';
import { TemperatureService } from 'src/app/services/temperature.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { DeviceService } from 'src/app/services/device.service';
import { Device } from 'src/app/beans/monitoring/device';

@Component({
  selector: 'app-batch-request-popup',
  templateUrl: './batch-request-popup.component.html',
  styleUrls: ['./batch-request-popup.component.css']
})
export class BatchRequestPopupComponent implements OnInit {

  units = [
    {
      name: 's',
      multiplicator: 1
    },
    {
      name: 'min',
      multiplicator: 60
    },
    {
      name: 'h',
      multiplicator: 3600
    },
    {
      name: 'day',
      multiplicator: 86400
    }
  ];
  request: BatchRequest;

  /**
   * Temporary stores form data
   */
  formData: any = {};
  SNACKBAR_POP_DURATION = 2000;

  constructor(
    public dialogRef: MatDialogRef<BatchRequestPopupComponent>,
    private snackBar: MatSnackBar,
    private deviceService: DeviceService,
    @Inject(MAT_DIALOG_DATA) public dialogInputData: any) { }

  ngOnInit() {
    this.request = {};
  }


  onNoClick() {
    this.dialogRef.close();
  }

  onSubmit() {
    const result: BatchRequest = {
      device: this.dialogInputData.device.uuid,
      frequency: this.formData.frequency * this.formData.frequencyUnit,
      duration: this.formData.duration * this.formData.durationUnit
    };

    console.log(result);
    this.deviceService.startDevice(this.dialogInputData.device, result).subscribe((ok: Device) => {
      this.snackBar.open('Device started !', null, {
        duration: this.SNACKBAR_POP_DURATION,
        panelClass: ['mat-snack-bar-ok']
      });
    }, (error: HttpErrorResponse) => {
      this.snackBar.open('Could not start device ! Error ' + error.statusText, null, {
        duration: this.SNACKBAR_POP_DURATION,
        panelClass: ['mat-snack-bar-error']
      });
    });
    this.dialogRef.close(result);
  }

}
