import { Injectable } from '@angular/core';
import { Ingredient } from '../beans/brewery/ingredient';
import { HttpClient } from '@angular/common/http';
import { Observable, merge } from 'rxjs';
import { environment } from '../../environments/environment';



@Injectable()
export class IngredientService {

  constructor(private http: HttpClient) { }


  public listAvailableMalts(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/malts');
  }

  public listAvailableHops(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/hops');
  }

  public listAvailableYeasts(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/yeasts');
  }
  public listAvailableSpices(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/spices');
  }

  public listAvailableAdditives(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/additives');
  }

  public getAllIngredients(): Observable<Ingredient[]> {
    return merge(
      this.listAvailableHops()
    , this.listAvailableMalts()
    , this.listAvailableYeasts()
    , this.listAvailableSpices()
    , this.listAvailableAdditives()
    );
  }
}
