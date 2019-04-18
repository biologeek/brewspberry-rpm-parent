import { Injectable } from '@angular/core';
import { Temperature } from '../beans/monitoring/temperature';
import { BatchRequest } from '../beans/monitoring/batch-request';
import { HttpClient, HttpRequest, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable()
export class TemperatureService {

  constructor(private http: HttpClient) { }


  public getTemperaturesForSensors(ids: Array<number>): Observable<Array<Temperature>> {
    const sensors: string = ids.join('.');
    return this.http.get(environment.apiHostMonitoring + '/sensors/' + sensors) as Observable<Array<Temperature>>;
  }
  public launchTemperatureMeasurement(req: BatchRequest): Observable<HttpResponse<void>> {
    return this.http.post(environment.apiHostMonitoring + '/run', req) as Observable<HttpResponse<void>>;
  }

  getTemperaturesForDeviceBetweenDates(uuid: string, dateBounds: Date[]): Observable<Temperature[]> {

    const prm: HttpParams = new HttpParams();
    prm.append('begin', '' + dateBounds[0].getTime());
    prm.append('end', '' + dateBounds[1].getTime());
    return (this.http.get(`${environment.apiHostMonitoring}/temperature/sensors/uuid/${uuid}/measurements?begin=${dateBounds[0].getTime()}&end=${dateBounds[1].getTime()}`) as Observable<Temperature[]>)
    .pipe(
      map(list => list.sort(this.sortTemperaturesByDate)
      ));
  }

  sortTemperaturesByDate(a: Temperature, b: Temperature) {
    if (a.date < b.date) {
      return -1;
    } else if (a.date > b.date){
      return 1;
    }
    return 0;
  }

}
