import { Injectable } from '@angular/core';
import { Temperature } from '../beans/monitoring/temperature';
import { BatchRequest } from '../beans/monitoring/batch-request';
import { HttpClient, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class TemperatureService {

  constructor(private http: HttpClient) { }


  public getTemperaturesForSensors(ids: Array<number>): Observable<Array<Temperature>> {
    const sensors: string = ids.join('.');
    return this.http.get(environment.apiTemperatureMonitoring + '/sensors/' + sensors) as Observable<Array<Temperature>>;
  }
  public launchTemperatureMeasurement(req: BatchRequest): Observable<HttpResponse<void>> {
    return this.http.post(environment.apiTemperatureMonitoring + '/run', req) as Observable<HttpResponse<void>>;
  }

}
