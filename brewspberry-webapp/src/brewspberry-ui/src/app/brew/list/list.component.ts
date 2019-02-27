import { Component, OnInit } from '@angular/core';
import { Brew } from 'src/app/beans/brew';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {


  brewList: Brew[];

  constructor() { }

  ngOnInit() {
  }


  deleteBrew(id: number) {

  }

}
