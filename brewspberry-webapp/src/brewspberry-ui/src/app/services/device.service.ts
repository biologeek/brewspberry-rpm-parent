import { Injectable } from '@angular/core';
import { Device } from '../beans/monitoring/device';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { BatchRequest } from '../beans/monitoring/batch-request';
import { DeviceTypes } from '../beans/monitoring/device-type';

@Injectable()
export class DeviceService {

  constructor(private http: HttpClient) { }

  public getAllDevices(): Observable<Device[]> {
    return <Observable<Device[]>> this.http.get(environment.apiHostMonitoring+'/devices/');
  }

  public getAvailableUuids(): Observable<string[]> {
    return this.http.get(environment.apiHostMonitoring + '/uuid/available') as Observable<string[]>;
  }


  public deleteDevice(uuid: string): Observable<string[]> { // TODO
    return this.http.delete(environment.apiHostMonitoring + '/uuid/' + uuid) as Observable<string[]>;
  }

  public update(device: Device): Observable<Device> { // TODO
    return this.http.put(environment.apiHostMonitoring + '/temperature/' + device.uuid, device) as Observable<Device>;
  }

  public startDevice(device: Device, batch: BatchRequest): Observable<Device> {
    return this.http.put(`${environment.apiHostMonitoring}/devices/${device.id}/start`, null) as Observable<Device>;
  }

  public stopDevice(device: Device): Observable<Device> {
    return this.http.put(`${environment.apiHostMonitoring}/devices/${device.id}/stop`, null) as Observable<Device>;
  }

  public getDeviceTypes(): Observable<DeviceTypes> {
    return this.http.get(`${environment.apiHostMonitoring}/devices/types`) as Observable<DeviceTypes>;
  }

  public saveDevice(device: Device): Observable<Device> {
    if (device.id){
      return this.http.put(`${environment.apiHostMonitoring}/devices/${device.id}`, device) as Observable<Device>;
    } else {
    return this.http.post(`${environment.apiHostMonitoring}/devices`, device) as Observable<Device>;
    }
  }
}
