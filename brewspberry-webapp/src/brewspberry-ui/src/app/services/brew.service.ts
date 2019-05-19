import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Brew } from 'src/app/beans/brewery/brew';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BrewService {

  constructor(private http: HttpClient) { }

  public getCurrentBrews(): Observable<Brew[]> {
    return this.http.get(`${environment.apiBreweryManagement}/brew`).pipe(map((brew: Brew) => this.convertDates(brew))) as Observable<Brew[]>;
  }

  public getAllBrews(): Observable<Brew[]> {
    return this.http.get(`${environment.apiBreweryManagement}/brew`).pipe(map((brew: Brew) => this.convertDates(brew))) as Observable<Brew[]>;
  }

  public getCurrentBrew(): Observable<Brew> {
    return this.http.get(`${environment.apiBreweryManagement}/brew/current`).pipe(map((brew: Brew) => this.convertDates(brew))) as Observable<Brew>;
  }

  public getBrew(id: number): Observable<Brew> {
    return this.http.get(`${environment.apiBreweryManagement}/brew/${id}`).pipe(map((brew: Brew) => this.convertDates(brew))) as Observable<Brew>;
  }

  private convertDates(brew: Brew): Brew {
    brew.beginning ? brew.beginning = new Date(brew.beginning) : null;
    brew.end ? brew.end = new Date(brew.end) : null;
    return brew;
  }
}
