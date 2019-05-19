import { Component, OnInit, Inject, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

import * as d3 from 'd3-selection';
import * as d3Scale from 'd3-scale';
import * as d3Axis from 'd3-axis';
import * as d3Shape from 'd3-shape';
import * as d3Time from 'd3-time-format';
import * as d3Format from 'd3-format';
import * as d3Svg from 'd3';
import { ChartData, Series } from 'src/app/beans/monitoring/chart-data';
import { TemperatureService } from 'src/app/services/temperature.service';
import { Temperature } from 'src/app/beans/monitoring/temperature';
import { Subscription } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { SwitchService } from 'src/app/services/switch.service';

@Component({
  selector: 'app-activity-chart-popup',
  templateUrl: './activity-chart-popup.component.html',
  styleUrls: ['./activity-chart-popup.component.css']
})
export class ActivityChartPopupComponent implements OnInit, OnDestroy {

  private chart;
  private dateBounds = [];
  private now = true;

  chartData: Temperature[];

  @ViewChild('activityChart')
  private chartAngularRef: ElementRef;

  effectiveHeight: number;
  effectiveWidth: number;

  chartReady = true;

  subscriptions$: Subscription[];

  private margins = {
    top: 10,
    bottom: 150,
    left: 70,
    right: 10
  }

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogInputData: any
  , private temperatureService: TemperatureService
  , private switchService: SwitchService
  , private http: HttpClient) { }

  ngOnInit() {
    this.subscriptions$ = [];
    // Initiating date to past 2 hours for the chart
    const past = new Date();
    past.setHours(past.getHours() - 2);
    this.dateBounds = [past, new Date()];
    /*this.http.get('assets/data.json').subscribe((a: Temperature[]) => {
      this.chartData = a;
      this.initChart();
    })*/
    this.getData();
    setInterval(() => {
      if (this.now){
        this.dateBounds[1] = new Date();
      }

      if (this.dialogInputData.device.state === 'STARTED'){
        this.getData();
      }
    }, 1000)
    
  }

  ngOnDestroy() {
    this.subscriptions$.forEach(s => s.unsubscribe());
  }

  refreshData() {
    this.getData();
  }

  getData() {
    if (this.dialogInputData.device.type === 'THERMOMETER') {
    const subscription$ = this.temperatureService
      .getTemperaturesForDeviceBetweenDates(this.dialogInputData.device.uuid, this.dateBounds)
      .subscribe(entity => {
        this.chartData = entity;
        this.chartReady = true;
        this.initChart();
      }, error => {

      });
      this.subscriptions$.push(subscription$);
    } else {
      const subscription$ = this.switchService.getSwitchStateBetweenDates(this.dialogInputData.device.uuid, this.dateBounds)
      .subscribe(entity => {
        this.chartData = entity;
        this.chartReady = true;
        this.initChart();
      })
    }

  }

  onCheckNow() {
    if (this.now){
      
    }
  }
  initChart() {
    d3.select('.main-g').remove();
    this.chart = d3.select('#chart');

    let tempEff = this.chartAngularRef.nativeElement.clientHeight - this.margins.top - this.margins.bottom;
    if (tempEff > 0) {
      this.effectiveHeight = tempEff;
    }
    tempEff = this.chartAngularRef.nativeElement.clientWidth - this.margins.left - this.margins.right;
    if (tempEff > 0 ) {
      this.effectiveWidth = tempEff;
    }

    const mainG = this.chart.append('g')//
      .attr('class', 'main-g')
      .attr('height', `${this.effectiveHeight}px`)//
      .attr('width', `${this.effectiveWidth}px`)
      .attr('transform', `translate(${(this.margins.left + this.margins.right) / 2}, ${(this.margins.top + this.margins.bottom) / 2})`)
      .attr('style', 'backgroung-color: red');

    const minAndMax = this.getMinAndMaxFromSeries(this.chartData);

    const x = d3Scale.scaleTime()//
      .range([0, this.effectiveWidth])
      .domain(minAndMax.x);

    const y = d3Scale.scaleLinear()//
      .range([0, this.effectiveHeight])
      .domain(minAndMax.y);

    const line = d3Shape//
      .line()//
      .x(item => x(new Date(item.date)))
      .y(item => y(item.temperature));
      
    const xAxis = d3Axis.axisBottom(x).ticks(10)
      .tickFormat(d3Time.timeFormat(`%d/%m/%y 
     %H:%M:%S`));


    const yAxis = d3Axis.axisLeft(y)
      .ticks(10)
      .tickFormat(d3Format.format(".1f"));


    /*
     * Adding elements to SVG
     */
    mainG.append('g')//
      .attr('class', 'axis axis-x')
      .attr('transform', 'translate(0,' + this.effectiveHeight + ')')
      .call(xAxis)
      .selectAll('text')
      .attr('transform', 'translate(-10,30)rotate(-45)');

    mainG.append('g')//
      .attr('class', 'axis axis-y')
      .call(yAxis);

      mainG.append('path')
      .datum(this.chartData)
      .attr('class', 'line')
      .attr('d', line)
      .attr('stroke', 'steelblue')
      .attr('fill', 'transparent');
      mainG.selectAll('.dots')
        .data(this.chartData)
        .enter().append('circle')
        .attr('r', '2')
        .attr('cx', (d, i) => { return x(d.date)})
        .attr('cy', (d, i) => { return y(d.temperature)})
        .attr('fill', 'blue');
    mainG.append('div')
      .attr('class', 'legend')
      .html('<p style="color: black">{{</p>')
  }

  /**
   * Returns the min and max values for X and Y among series sharing the same unit
   * @param series one or more series
   */
  private getMinAndMaxFromSeries(series: Temperature[]): any {
    const result = {
      x: [new Date(), new Date(0)],
      y: [Number.NEGATIVE_INFINITY, Number.POSITIVE_INFINITY]
    };

    for (let temp of series) {
      if (temp.date < result.x[0].getTime()) {
        result.x[0] = new Date(temp.date);
      } else if (temp.date > result.x[1].getTime()) {
        result.x[1] = new Date(temp.date);
      }

      if (temp.temperature < result.y[1]) {
        result.y[1] = temp.temperature;
      } else if (temp.temperature > result.y[0]) {
        result.y[0] = temp.temperature;
      }
    }
    return result;
    /*
  
  Following works for series of temperatures. IT SHOULD NOT BE ERASED !!!!
  
      const result = {
        x: [Number.POSITIVE_INFINITY, Number.NEGATIVE_INFINITY],
        y: [Number.POSITIVE_INFINITY, Number.NEGATIVE_INFINITY]
      };
  
      for (let serie of series) {
        for (let xyValue of serie.points) {
          if (xyValue.x > result.x[1]) {
            result.x[1] = xyValue.x;
          } else if (xyValue.x < result.x[0]) {
            result.x[0] = xyValue.x;
          }
          if (xyValue.y > result.y[1]) {
            result.y[1] = xyValue.y;
          } else if (xyValue.y < result.y[0]) {
            result.y[0] = xyValue.y;
          }
        }
      }
  
      */
  }
}
