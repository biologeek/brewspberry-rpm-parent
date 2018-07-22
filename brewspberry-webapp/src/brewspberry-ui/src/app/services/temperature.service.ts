import { Injectable } from '@angular/core';
import { Temperature } from '../beans/temperature';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class TemperatureService {

  constructor(private http: HttpClient) { }


  public getTemperaturesForSensors(ids: Array<number>): Observable<Temperature[]> {
  	var sensors: string = ids.join(".");
  	return <Observable<Temperature[]>> this.http.get(environment.apiTemperatureMonitoring + '/sensors/' + sensors);
  }

}
