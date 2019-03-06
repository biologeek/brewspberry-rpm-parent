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
      type: 'thermometer',
      uuid: '28-012ADE43FB642',
      temperature: 25.0,
      state: 'STARTED'
    }, {
      sensor: 2,
      name: 'Device 2',
      type: 'thermometer',
      uuid: '28-012ADE43FB642',
      temperature: 21.0,
      state: 'STARTED'
    }, {
      sensor: 3,
      name: 'Device 3',
      type: 'thermometer',
      temperature: 93.0,
      uuid: '28-012ADE43FB642',
      state: 'STOPPED'
    }, {
      id: 4,
      name: 'ENG-004',
      type: 'engine',
      uuid: '1A2B3C4D5F',
      pin: 'GPIO05',
      isActive: true,
      state: 'UP'
    }, {
      id: 4,
      name: 'PMP-005',
      type: 'pump',
      uuid: '1A2B3C4D5F',
      pin: 'GPIO05',
      isActive: true,
      state: 'DOWN'
    }, {
      id: 4,
      name: 'PMP-006',
      type: 'pump',
      uuid: '1A2B3C4D5F',
      pin: 'GPIO05',
      isActive: true,
      state: 'UP'
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
