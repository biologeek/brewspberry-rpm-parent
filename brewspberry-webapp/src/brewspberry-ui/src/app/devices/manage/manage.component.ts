import { Component, OnInit } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { TemperatureService } from '../../services/temperature.service';
import { Device } from '../../beans/device';
import { Temperature } from '../../beans/temperature';
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

  private MAX_DEVICE_PER_COLUMN = 3;
  private devices: Object[][];
  private rawDevices: Device[];
  private temperatures: Temperature[];

  constructor(private deviceService: DeviceService, private toast: ToastrService, private temperatureService: TemperatureService) { }

  ngOnInit() {
    //Get all devices
    this.deviceService.getAllDevices().subscribe(data => {
        data = this.mockDevices();
        this.rawDevices = data;
        this.devices = [];
        this.processDevices(data);
        this.temperatureService.getTemperaturesForSensors(this.rawDevices.map(a => a.id)).subscribe(data => {
          this.temperatures = this.mockTemperatures();
        });
      }, error => {
        this.devices = [];
        this.rawDevices = this.mockDevices();
        this.processDevices(this.rawDevices);
        this.toast.error("Could not get devices !");
        this.temperatureService.getTemperaturesForSensors(this.rawDevices.map(a => a.id)).subscribe(data => {
          this.temperatures = this.mockTemperatures();
          this.processTemperature();
        }, error => {
          this.temperatures = this.mockTemperatures();
          this.processTemperature();
        });
      }
    ); 
  }


  public toggleDevice(device: Device): void {
    let toToggle: Device[] = this.rawDevices.filter(o => o.id === device.id);
   // if (toToggle && toToggle.length == 1){
   //   toToggle[0].state = toToggle[0].state == 'UP' ? 'DOWN' : 'UP';
   //   }
    this.deviceService.update(toToggle[0]).subscribe(response => {
      toToggle[0] = response
    }, error => {
      this.toast.error("Could not update device "+toToggle[0].uuid);
    })
  }

  private processTemperature(): void {
    for (let dvcArr of this.devices){
      for(let dvc of dvcArr){
        let dvcCast: Device = <Device> dvc;
        var tpr: Temperature[] = this.temperatures.filter(a => a.sensor == dvcCast.id);
        dvcCast.temperature = tpr ? tpr[0].temperature : "N/A";
      }
    }
  }

  private mockTemperatures(){
    let temp1 : Temperature = {
      "sensor": 1,
      "temperature": "25.0"
    }
    let temp2 : Temperature = {
      "sensor": 2,
      "temperature": "21.0"
    }
    let temp3 : Temperature = {
      "sensor": 3,
      "temperature": "93.0"
    }

    return [temp1, temp2, temp3];
  }

  private processDevices(data : Device[]){
    for(var i: number = 0 ; i< data.length ; i++){
        if (i%this.MAX_DEVICE_PER_COLUMN == 0){
          this.devices.push([data[i]]);
          data[i+1] ? this.devices[this.devices.length - 1].push(data[i+1]) : null;
          data[i+2] ? this.devices[this.devices.length - 1].push(data[i+2]) : null;
        }
     }

     var lastGroup = this.devices[this.devices.length - 1];
     if (lastGroup.length < this.MAX_DEVICE_PER_COLUMN){
       this.devices[this.devices.length - 1].push("LAST");
     } else {
       this.devices.push(["LAST"]);
     }
  }



  private deleteDevice(deviceUUID: string){
      this.deviceService.deleteDevice(deviceUUID).subscribe(data => {
        this.toast.info("Device removed");
      }, error => {
        this.toast.warning("Error when deleting device")
      });
  }


  /***************
   ** TEST DATA **
   **************/


  public mockDevices() : Device[] {
    let dev1 : Device = {
      "id" : 1,
      "name": "DS18-001",
      "uuid" : "1A2B3C4D5E",
      "pin" : "GPIO04",
      "type" : "DS18B20",
      "isActive": false,
      "state": "STOPPED"
    };
    let dev2 : Device = {
      "id" : 2,
      "name": "DS18-002",
      "uuid" : "1A2B3C4D5F",
      "pin" : "GPIO05",
      "type" : "DS18B20",
      "isActive": true,
      "state": "STARTED"
    };
    let dev3 : Device = {
      "id" : 3,
      "name": "DS18-003",
      "type" : "VALVE",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "state": "DOWN"
    };
    let dev4 : Device = {
      "id" : 2,
      "type" : "ENGINE_RELAY",
      "name": "ENG-004",
      "uuid" : "1A2B3C4D5F",
      "pin" : "GPIO05",
      "isActive": true,
      "state": "DOWN"
    };
    let dev5 : Device = {
      "id" : 3,
      "type" : "DS18B20",
      "name": "DS18-003",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "state": "IDLE"
    };
    let dev6 : Device = {
      "id" : 6,
      "name": "DS18-003",
      "type" : "VALVE",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "state": "UP"
    };
    let dev7 : Device = {
      "id" : 7,
      "name": "ENG001",
      "type" : "ENGINE_RELAY",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "state": "PAUSED"
    };
    let dev8 : Device = {
      "id" : 7,
      "name": "ENG001",
      "type" : "ENGINE_RELAY",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "state": "STARTED"
    };
    let dev9 : Device = {
      "id" : 7,
      "name": "ENG001",
      "type" : "VALVE",
      "uuid" : "1A2B3C3D5E",
      "pin" : "GPIO03",
      "isActive": true,
      "state": "DOWN"
    };
    return [dev1, dev2, dev3, dev4, dev5, dev6, dev7, dev8, dev9];
  }

}
