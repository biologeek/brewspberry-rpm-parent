import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { RaspberryService } from 'src/app/services/raspberry.service';
import { Pins } from 'src/app/beans/monitoring/pin';
import { DeviceTypes } from 'src/app/beans/monitoring/device-type';
import { Observable } from 'rxjs';
import { DeviceService } from 'src/app/services/device.service';
import { Device } from 'src/app/beans/monitoring/device';

@Component({
  selector: 'app-add-device-popup',
  templateUrl: './add-device-popup.component.html',
  styleUrls: ['./add-device-popup.component.css']
})
export class AddDevicePopupComponent implements OnInit {

  pinPictureShown: boolean;

  pins$: Observable<Pins>;
  types$: Observable<DeviceTypes>;

  device: Device;

  constructor(
      private raspberryService: RaspberryService
    , private deviceService: DeviceService
    , private dialogRef: MatDialogRef<AddDevicePopupComponent>
    , private snackbar: MatSnackBar
    , @Inject(MAT_DIALOG_DATA) public dialogInputData: any) { }

  ngOnInit() {
    if (this.dialogInputData.device){
      this.device = this.dialogInputData.device;
    } else {
      this.device = new Device();
    }
    this.types$ = this.deviceService.getDeviceTypes();
    this.pins$ = this.raspberryService.getAllPins();
  }

  showPinPicture() {
    this.pinPictureShown = true;
  }

  hidePinPicture() {
    this.pinPictureShown = false;
  }


  cancel() {
    this.dialogRef.close();
  }

  onSubmit() {
    this.deviceService.saveDevice(this.device).subscribe(response => {
      this.snackbar.open("Device saved !", null, {
        duration: 3000,
        panelClass: ['mat-snack-bar-ok']
      });
      this.dialogRef.close(response);
    }, error => {
      this.snackbar.open("Could not save device !", null, {
        duration: 3000,
        panelClass: ['mat-snack-bar-error']
      });
      this.dialogRef.close();
    });

  }

}
