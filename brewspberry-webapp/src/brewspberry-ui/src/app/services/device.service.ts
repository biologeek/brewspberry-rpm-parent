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

}
