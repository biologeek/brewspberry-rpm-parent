import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  private sidebarHidden: boolean =false;
  constructor() { }

  ngOnInit() {
  }


  private toggleSidebar(){
  	this.sidebarHidden = !this.sidebarHidden;
  }

}
