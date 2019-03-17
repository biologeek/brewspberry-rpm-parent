import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pins } from '../beans/monitoring/pin';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RaspberryService {

  constructor(private http: HttpClient) { }


  public getAllPins(): Observable<Pins> {
    return this.http.get(`${environment.apiHostMonitoring}/raspberry/pins`) as Observable<Pins>;
  }
}
