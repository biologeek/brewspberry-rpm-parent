import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { TemperatureService } from '../../services/temperature.service';
import { Device } from '../../beans/monitoring/device';
import { Temperature } from '../../beans/monitoring/temperature';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { MatDialog, MatDialogRef, MatSnackBarRef, MatSnackBar } from '@angular/material';
import { BatchRequestPopupComponent } from '../batch-request-popup/batch-request-popup.component';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { AddDevicePopupComponent } from '../add-device-popup/add-device-popup.component';
import { ActivityChartPopupComponent } from '../activity-chart-popup/activity-chart-popup.component';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css'],
  encapsulation: ViewEncapsulation.None
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

  private MAX_DEVICE_PER_COLUMN = 3;
  private devices$: Observable<Device[]>;
  private rawDevices: Device[];
  private temperatures: Temperature[];
  private currentBatchDialogRef: MatDialogRef<any, any>;
  private addDeviceDialogRef: MatDialogRef<any, any>;
  private chartDialogRef: MatDialogRef<any, any>;

  SNACKBAR_POP_DURATION = 2000;

  constructor(//
    private deviceService: DeviceService, //
    private snackBar: MatSnackBar,
    private temperatureService: TemperatureService,
    public batchRequestDialog: MatDialog,
    public chartDialog: MatDialog,
    public addDeviceDialog: MatDialog) { }

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

  private deleteDevice(deviceUUID: string) {
    this.deviceService.deleteDevice(deviceUUID).subscribe(data => {
      this.snackBar.open('Device removed', null, {
        duration: this.SNACKBAR_POP_DURATION,
        panelClass: ['mat-snack-bar-ok']
      });
    }, error => {
      this.snackBar.open('Error when deleting device !!', null, {
        duration: this.SNACKBAR_POP_DURATION,
        panelClass: ['mat-snack-bar-error']
      });
    });
  }

  stopDevice(device) {
    this.deviceService.stopDevice(device).subscribe(data => {
      this.snackBar.open('Device stopped', null, {
        duration: this.SNACKBAR_POP_DURATION,
        panelClass: ['mat-snack-bar-ok']
      });
    }, error => {
      this.snackBar.open('Could not stop device !!', null, {
        duration: this.SNACKBAR_POP_DURATION,
        panelClass: ['mat-snack-bar-error']
      });
    });
  }

  /**
   * Opens the popup to specify duration and measurement frequency and launch device
   *
   * @param device the device to start
   */
  openBatchRequestPopup(deviceToOpen: Device) {
    this.currentBatchDialogRef = this.batchRequestDialog.open(BatchRequestPopupComponent, {
      width: '50%',
      data: {
        device: deviceToOpen
      }/*,
      height: '500px'*/
    });
  }

  openAddDevice() {
    this.addDeviceDialogRef = this.addDeviceDialog.open(AddDevicePopupComponent, {
      width: '30%',
      data: {
        device: null
      }
    });
  }

  editDevice(device: Device) {
    this.addDeviceDialogRef = this.addDeviceDialog.open(AddDevicePopupComponent, {
      width: '30%',
      data: {
        device: device
      }
    });
  }

  openChartPopup(deviceToOpen: Device) {
    this.chartDialogRef = this.chartDialog.open(ActivityChartPopupComponent, {
      width: '90%',
      height: '90%',
      data: {
        device: deviceToOpen
      }
    });
  }
}
