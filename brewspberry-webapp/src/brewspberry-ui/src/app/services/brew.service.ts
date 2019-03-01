import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Brew } from '../beans/brewery/brew';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BrewService {

  constructor(private http: HttpClient) { }



  public getCurrentBrews(): Observable<Brew[]> {
    return this.http.get(`${environment.apiBreweryManagement}/brew`) as Observable<Brew[]>;
  }

}
