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

  breakpoint: number;

  BREAKPOINT_SIZE = 600;

  private MAX_DEVICE_PER_COLUMN = 3;
  private devices$: Observable<Device[]>;
  private rawDevices: Device[];
  private temperatures: Temperature[];

  constructor(private deviceService: DeviceService, private toast: ToastrService, private temperatureService: TemperatureService) { }

  ngOnInit() {
    // Get all devices, then last temperature for each device
    this.devices$ = this.deviceService.getAllDevices();
    this.onResize();
  }
  onResize() {
    if (window.innerWidth > 1400) {
      this.breakpoint = 4;
    } else if (window.innerWidth > 900) {
      this.breakpoint = 3;
    } else if (window.innerWidth > 600) {
      this.breakpoint = 2;
    } else if (window.innerWidth <= 600) {
      this.breakpoint = 1;
    }
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
      this.toast.warning('Error when deleting device');
    });
  }

}
