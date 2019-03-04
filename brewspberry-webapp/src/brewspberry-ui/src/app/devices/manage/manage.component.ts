import { Component, OnInit } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { TemperatureService } from '../../services/temperature.service';
import { Device } from '../../beans/monitoring/device';
import { Temperature } from '../../beans/monitoring/temperature';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

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
  private devices$: Observable<Device[]>;
  private rawDevices: Device[];
  private temperatures: Temperature[];

  constructor(private deviceService: DeviceService, private toast: ToastrService, private temperatureService: TemperatureService) { }

  ngOnInit() {
    // Get all devices, then last temperature for each device
    this.devices$ = this.deviceService.getAllDevices();
  }


  public toggleDevice(device: Device): void {
    const toToggle: Device[] = this.rawDevices.filter(o => o.id === device.id);
    // if (toToggle && toToggle.length == 1){
    //   toToggle[0].state = toToggle[0].state == 'UP' ? 'DOWN' : 'UP';
    //   }
    this.deviceService.update(toToggle[0]).subscribe(response => {
      toToggle[0] = response;
    }, error => {
      this.toast.error('Could not update device ' + toToggle[0].uuid);
    })
  }



  private deleteDevice(deviceUUID: string) {
    this.deviceService.deleteDevice(deviceUUID).subscribe(data => {
      this.toast.info('Device removed');
    }, error => {
      this.toast.warning('Error when deleting device')
    });
  }


  /***************
   ** TEST DATA **
   **************/


  public mockDevices(): Device[] {
    const dev1: Device = {
      id: 1,
      name: 'DS18-001',
      uuid: '1A2B3C4D5E',
      pin: 'GPIO04',
      type: 'DS18B20',
      isActive: false,
      state: 'STOPPED'
    };
    const dev2: Device = {
      id: 2,
      name: 'DS18-002',
      uuid: '1A2B3C4D5F',
      pin: 'GPIO05',
      type: 'DS18B20',
      isActive: true,
      state: 'STARTED'
    };
    const dev3: Device = {
      id: 3,
      name: 'DS18-003',
      type: 'VALVE',
      uuid: '1A2B3C3D5E',
      pin: 'GPIO03',
      isActive: true,
      state: 'DOWN'
    };
    const dev4: Device = {
      id: 4,
      name: 'ENGINE_RELAY',
      type: 'ENG-004',
      uuid: '1A2B3C4D5F',
      pin: 'GPIO05',
      isActive: true,
      state: 'DOWN'
    };
    return [dev1, dev2, dev3, dev4];
  }

}
