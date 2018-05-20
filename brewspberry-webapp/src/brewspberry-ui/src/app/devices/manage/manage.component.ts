import { Component, OnInit } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { Device } from '../../beans/device';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css']
})
export class ManageComponent implements OnInit {

  model: any = {
    onColor: 'primary',
    offColor: 'secondary',
    onText: 'On',
    offText: 'Off',
    disabled: false,
    size: 'sm',
    value: false
  };

  private const MAX_DEVICE_PER_COLUMN = 3;
  private devices: Array<Array<Object>;

  constructor(private deviceService: DeviceService, private toast: ToastrService) { }

  ngOnInit() {
    //Get all devices
    this.deviceService.getAllDevices().subscribe(data => {
      data = this.mockDevices();
      this.devices = [];
      this.processDevices(data);
      
    }, error => {
      this.devices = [];
      this.processDevices(this.mockDevices());
      this.toast.error("Could not get devices !");
    });
  }

  private processDevices(data : Device[]){
    for(var i: number = 0 ; i< data.length ; i++){
        if (i%this.MAX_DEVICE_PER_COLUMN == 0){
          this.devices.push([data[i], data[i+1] || null, data[i+2] || null);
        }
     }

     var lastGroup = this.devices[this.devices.length - 1];
     if (lastGroup[lastGroup.length - 1] == null){
       this.devices[this.devices.length - 1][lastGroup.length - 1] = "LAST";
     } else {
       this.devices.push(["LAST"]);
     }
  }


  public mockDevices() : Device[] {
    let dev1 : Device = {
      "id" : 1,
      "name": "DS18-001",
      "uuid" : "1A2B3C4D5E",
      "pin" : "GPIO04",
      "type" : "DS18B20",
      "temperature" : 33.9,
      "isActive": false,
      "state": "IDLE"
    };
    let dev2 : Device = {
      "id" : 2,
      "name": "DS18-002",
      "uuid" : "1A2B3C4D5F",
      "pin" : "GPIO05",
      "type" : "DS18B20",
      "temperature" : 528.9,
      "isActive": true,
      "state": "IDLE"
    };
    let dev3 : Device = {
      "id" : 3,
      "name": "DS18-003",
      "type" : "VALVE",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "temperature" : 50.9,
      "state": "PAUSED"
    };
    let dev4 : Device = {
      "id" : 2,
      "type" : "ENGINE_RELAY",
      "name": "DS18-004",
      "uuid" : "1A2B3C4D5F",
      "pin" : "GPIO05",
      "isActive": true,
      "temperature" : 50.9,
      "state": "STOPPED"
    };
    let dev5 : Device = {
      "id" : 3,
      "type" : "DS18B20",
      "name": "DS18-003",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "temperature" : 50.9,
      "state": "IDLE"
    };
    return [dev1, dev2, dev3, dev4, dev5];
  }

}
