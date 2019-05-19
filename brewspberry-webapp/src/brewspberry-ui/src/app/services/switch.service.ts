import { Injectable } from '@angular/core';
import { Temperature } from '../beans/monitoring/temperature';
import { Observable } from 'rxjs';
import { HttpParams, HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SwitchService {

  constructor(private http: HttpClient) { }

  public getSwitchStateBetweenDates(uuid: string, dateBounds: Date[]): Observable<Temperature[]> {

    const prm: HttpParams = new HttpParams();
    prm.append('begin', '' + dateBounds[0].getTime());
    prm.append('end', '' + dateBounds[1].getTime());
    return (this.http.get(`${environment.apiHostMonitoring}/switch/sensors/uuid/${uuid}/measurements?begin=${dateBounds[0].getTime()}&end=${dateBounds[1].getTime()}`) as Observable<Temperature[]>)
    .pipe(
      map(list => list.sort(this.sortByDate)
      ));
  }

  sortByDate(a: Temperature, b: Temperature) {
    if (a.date < b.date) {
      return -1;
    } else if (a.date > b.date){
      return 1;
    }
    return 0;
  }
}
