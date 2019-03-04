import { Injectable } from '@angular/core';
import { Device } from '../beans/device';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class DeviceService {

  constructor(private http: HttpClient) { }

  public getAllDevices (): Observable<Device[]> {
  	return <Observable<Device[]>> this.http.get(environment.apiHostMonitoring+'/');
  }

  public getAvailableUuids(): Observable<string[]> {
  	return <Observable<string[]>> this.http.get(environment.apiHostMonitoring + '/uuid/available');
  }


  public deleteDevice(uuid: string): Observable<string[]> { // TODO
    return <Observable<string[]>> this.http.delete(environment.apiHostMonitoring + '/uuid/'+uuid);
  }

  public update(device: Device): Observable<Device> { // TODO
    return <Observable<Device>> this.http.put(environment.apiHostMonitoring + '/temperature/'+device.uuid, device);
  }

}
