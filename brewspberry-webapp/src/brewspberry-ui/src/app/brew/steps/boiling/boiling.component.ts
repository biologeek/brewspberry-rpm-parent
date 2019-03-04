import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Brew } from 'src/app/beans/brewery/brew';

@Component({
  selector: 'app-boiling',
  templateUrl: './boiling.component.html',
  styleUrls: ['./boiling.component.css']
})
export class BoilingComponent implements OnInit {

  public currentBrew: Brew;

  constructor(viewContainerRef: ViewContainerRef) { }

  ngOnInit() {
  }

}
