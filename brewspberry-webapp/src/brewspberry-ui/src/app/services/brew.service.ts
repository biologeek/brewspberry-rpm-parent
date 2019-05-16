import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Brew } from 'src/app/beans/brewery/brew';

@Injectable({
  providedIn: 'root'
})
export class BrewService {

  constructor(private http: HttpClient) { }

  public getCurrentBrews(): Observable<Brew[]> {
    return this.http.get(`${environment.apiBreweryManagement}/brew`) as Observable<Brew[]>;
  }

  public getAllBrews(): Observable<Brew[]> {
    return this.http.get(`${environment.apiBreweryManagement}/brew`) as Observable<Brew[]>;
  }

  public getCurrentBrew(): Observable<Brew> {
    return this.http.get(`${environment.apiBreweryManagement}/brew/current`) as Observable<Brew>;
  }

  public getBrew(id: number): Observable<Brew> {
    return this.http.get(`${environment.apiBreweryManagement}/brew/${id}`) as Observable<Brew>;
  }

}
