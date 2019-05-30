import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable, Subscription, of } from 'rxjs';
import { StepStage } from '../beans/brewery/step-stage';
import { Step } from '../beans/brewery/step';
import { StepIngredient } from '../beans/brewery/step-ingredient';


@Injectable()
export class StepService {

    cur: Observable<StepStage[]> = of([{
        'id': 1,
        'stageType': 'CONSTANT',
        'duration': 60,
        'beginningSetPoint': 34,
        'endSetPoint': 34,
        'label': 'ABCDEF'
    }]);

    constructor(private http: HttpClient) { }


    public getStagesForStep(stepId: number): Observable<StepStage[]> {
        return <Observable<StepStage[]>> this.http.get(`${environment.apiBreweryManagement}/step/${stepId}/stages`);
    }


    public pushNewStage(step: Step, stage: StepStage): Observable<StepStage[]> {
        return <Observable<StepStage[]>> this.http.post(`${environment.apiBreweryManagement}/step/${step.id}/stages`, stage);
        // return of(step.stages.concat(stage));
    }

    public pushNewIngredient(step: number, ingredient: StepIngredient): Observable<StepIngredient> {
        return <Observable<StepIngredient>> this.http.post(`${environment.apiBreweryManagement}/step/${step}/ingredient`, ingredient);
        //return of(step.stages.concat(stage));
    }


    public getStep(id: number): Observable<Step> {
        return this.http.get(`${environment.apiBreweryManagement}/step/${id}`);
    }

    public calculateStageDates(obj: Step[]) {
        for (let step of obj) {
            for (let stage of step.stages) {
                stage.beginningToStep = stage.beginning - step.beginning;
                console.log('stage ' + stage.id + ' started '+ stage.beginningToStep / 1000 +' s after step');
            }
        }
    }
}
