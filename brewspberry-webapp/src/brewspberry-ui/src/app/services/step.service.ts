import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable, Subscription, of } from 'rxjs';
import { StepStage } from '../beans/brewery/step-stage';
import { Step } from '../beans/brewery/step';


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
       // return <Observable<StepStage[]>> this.http.get(`${environment.apiBreweryManagement}/step/${stepId}/stages`);

       return this.cur;
    }


    public pushNewStage(step: Step, stage: StepStage): Observable<StepStage[]> {
        // return <Observable<StepStage[]>> this.http.post(`${environment.apiBreweryManagement}/step/${step.id}/stages`, stage);
        return of(step.stages.concat(stage));
    }
}
