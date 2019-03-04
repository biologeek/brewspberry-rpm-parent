import { Injectable } from '@angular/core';
import { Device } from '../beans/monitoring/device';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class DeviceService {

  constructor(private http: HttpClient) { }

  public getAllDevices(): Observable<Device[]> {
    return of([{
      sensor: 1,
      name: 'Device 1',
      temperature: 25.0
    }, {
      sensor: 2,
      name: 'Device 2',
      temperature: 21.0
    }, {
      sensor: 3,
      name: 'Device 3',
      temperature: 93.0
    }]);
    // return <Observable<Device[]>> this.http.get(environment.apiHostMonitoring+'/');
  }

  public getAvailableUuids(): Observable<string[]> {
    return <Observable<string[]>>this.http.get(environment.apiHostMonitoring + '/uuid/available');
  }


  public deleteDevice(uuid: string): Observable<string[]> { // TODO
    return <Observable<string[]>>this.http.delete(environment.apiHostMonitoring + '/uuid/' + uuid);
  }

  public update(device: Device): Observable<Device> { // TODO
    return <Observable<Device>>this.http.put(environment.apiHostMonitoring + '/temperature/' + device.uuid, device);
  }

}
