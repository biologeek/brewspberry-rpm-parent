import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

import * as d3 from 'd3-selection';

@Component({
  selector: 'app-activity-chart-popup',
  templateUrl: './activity-chart-popup.component.html',
  styleUrls: ['./activity-chart-popup.component.css']
})
export class ActivityChartPopupComponent implements OnInit {

  private chart;

  constructor(@Inject(MAT_DIALOG_DATA) public dialogInputData: any) { }

  ngOnInit() {
    this.initChart();
  }


  initChart() {
    this.chart = d3.select('#chart');
    this.chart.append('g');
  }

}
