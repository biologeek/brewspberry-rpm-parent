import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Brew } from 'src/app/beans/brewery/brew';
import { BrewService } from 'src/app/services/brew.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {



  displayedColumns: string[] = ['date', 'title', 'state', 'actions'];

  brewList: Brew[];

  constructor(private router: Router, private brewService: BrewService) { }

  ngOnInit() {
    this.brewService.getAllBrews().subscribe(brews => {
      this.brewList = brews;
    });
  }

  navigateToBrew(rowId) {
    this.router.navigate([`/brew/${rowId}`])
  }
  deleteBrew(id: number) {

  }

}
