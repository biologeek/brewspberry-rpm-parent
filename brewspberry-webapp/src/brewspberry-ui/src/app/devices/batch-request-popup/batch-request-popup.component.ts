import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BatchRequest } from 'src/app/beans/monitoring/batch-request';

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

  constructor(public dialogRef: MatDialogRef<BatchRequestPopupComponent>, @Inject(MAT_DIALOG_DATA) public dialogInputData: any) { }

  ngOnInit() {
    this.request = {};
  }

  onSubmit() {
    const result = {
      devices: this.dialogInputData.devices,
      frequency: this.formData.frequency * this.formData.frequencyUnit,
      duration: this.formData.duration * this.formData.durationUnit
    };
    this.dialogRef.close(result);
  }

}
