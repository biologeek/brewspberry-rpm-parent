import { Injectable } from '@angular/core';
import { Temperature } from '../beans/temperature';
import { BatchRequest } from '../beans/batch-request';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class TemperatureService {

  constructor(private http: HttpClient) { }


  public getTemperaturesForSensors(ids: Array<number>): Observable<Array<Temperature>> {
  	var sensors: string = ids.join(".");
  	return <Observable<Array<Temperature>>> this.http.get(environment.apiTemperatureMonitoring + '/sensors/' + sensors);
  }
  public launchTemperatureMeasurement(req: BatchRequest): Observable<void> {
  	return <Observable<any>> this.http.post(environment.apiTemperatureMonitoring + '/run', req);
  }

}
