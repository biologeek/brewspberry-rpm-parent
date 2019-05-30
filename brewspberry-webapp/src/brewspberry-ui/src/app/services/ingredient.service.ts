import { Injectable } from '@angular/core';
import { Ingredient } from '../beans/brewery/ingredient';
import { HttpClient } from '@angular/common/http';
import { Observable, merge, combineLatest } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';



@Injectable()
export class IngredientService {

  constructor(private http: HttpClient) { }


  public listAvailableMalts(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/ingredients/malts');
  }

  public listAvailableHops(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/ingredients/hops');
  }

  public listAvailableYeasts(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/ingredients/yeasts');
  }
  public listAvailableSpices(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/ingredients/spices');
  }

  public listAvailableAdditives(): Observable<Ingredient[]> {
    return <Observable<Ingredient[]>>this.http.get(environment.apiBreweryManagement + '/ingredients/additives');
  }

  public getAllIngredients(): Observable<Ingredient[]> {
    return combineLatest(
      this.listAvailableHops()
    , this.listAvailableMalts()
    , this.listAvailableYeasts()
    , this.listAvailableSpices()
    , this.listAvailableAdditives()
    ).pipe(map((value: Ingredient[][]) => [...value[0], ...value[1], ...value[2], ...value[3], ...value[4]]));
  }
}
