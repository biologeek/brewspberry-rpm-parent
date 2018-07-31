import { Component, OnInit } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import {Device} from '../../beans/device';

@Component({
  selector: 'app-creation-form',
  templateUrl: './creation-form.component.html',
  styleUrls: ['./creation-form.component.css']
})
export class CreationFormComponent implements OnInit {


  private availableUuids: string[] = [];
  private device: Device = new Device();

  constructor(private deviceService: DeviceService) { }

  ngOnInit() {
  	// Gets plugged & available devices
  	this.deviceService.getAvailableUuids().subscribe(data => {
  		this.availableUuids = data;
  	}, error => {
  		this.availableUuids = this.mockUUIDs();
  	})
  }
  private mockUUIDs(): string[]{
  	return [
  	  '818ce4d8-8dc1-11e8-9eb6-529269fb1459'
  	, '818ce78a-8dc1-11e8-9eb6-529269fb1459'
  	, '818ce9ec-8dc1-11e8-9eb6-529269fb1459'
  	, '818cec12-8dc1-11e8-9eb6-529269fb1459'
  	, '818ced8e-8dc1-11e8-9eb6-529269fb1459'];
  }

}
