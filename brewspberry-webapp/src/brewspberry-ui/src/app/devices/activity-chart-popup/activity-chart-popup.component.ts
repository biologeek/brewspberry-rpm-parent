import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-activity-chart-popup',
  templateUrl: './activity-chart-popup.component.html',
  styleUrls: ['./activity-chart-popup.component.css']
})
export class ActivityChartPopupComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public dialogInputData: any) { }

  ngOnInit() {
  }

}
