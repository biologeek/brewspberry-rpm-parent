import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ManageComponent } from './devices/manage/manage.component';
import { CreationFormComponent } from './devices/creation-form/creation-form.component';
import { ListComponent } from './brew/list/list.component';
import { CreateComponent } from './brew/create/create.component';
import { ParamsComponent } from './params/params.component';
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { DashboardComponent } from './dashboard/dashboard.component';


import { DeviceService } from './services/device.service';
import { TemperatureService } from './services/temperature.service';
import { IngredientService } from './services/ingredient.service';



import { ToastrModule } from 'ngx-toastr';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxToggleModule } from 'ngx-toggle';
import { MashingComponent } from './brew/steps/mashing/mashing.component';
import { LauteringComponent } from './brew/steps/lautering/lautering.component';
import { BoilingComponent } from './brew/steps/boiling/boiling.component';
import { FermentationComponent } from './brew/steps/fermentation/fermentation.component';
import { SecondFermentationComponent } from './brew/steps/second-fermentation/second-fermentation.component';
import { ConditioningComponent } from './brew/steps/conditioning/conditioning.component';
import { InputsComponent } from './brew/steps/inputs/inputs.component';
import { FilteringComponent } from './brew/steps/filtering/filtering.component';
import { StepService } from './services/step.service';

import { MatTableModule, MatCardModule, MatGridListModule, MatButtonModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';



export const router: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login', component: LoginComponent },
    { path: 'devices/manage', component: ManageComponent },
    { path: 'devices/create', component: CreationFormComponent },
    { path: 'devices/edit/:id', component: CreationFormComponent },
    { path: 'brew/list', component: ListComponent },
    { path: 'brew/create', component: CreateComponent },
    { path: 'brew/create/:selectedStep', component: CreateComponent },
    { path: 'params', component: ParamsComponent },
    { path: 'dashboard', component: DashboardComponent }

];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ManageComponent,
    CreationFormComponent,
    ListComponent,
    CreateComponent,
    ParamsComponent,
    SidebarComponent,
    DashboardComponent,
    MashingComponent,
    LauteringComponent,
    BoilingComponent,
    FermentationComponent,
    SecondFermentationComponent,
    ConditioningComponent,
    InputsComponent,
    FilteringComponent
    ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(router),
    TooltipModule.forRoot(),
    TypeaheadModule.forRoot(),
    NgxToggleModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    FlexLayoutModule,
    MatTableModule,
    MatGridListModule,
    MatCardModule,
    MatButtonModule
  ],
  providers: [DeviceService, TemperatureService, IngredientService, StepService],
  exports: [RouterModule],
  bootstrap: [AppComponent],
  entryComponents: [
    MashingComponent,
    LauteringComponent,
    BoilingComponent,
    FermentationComponent,
    SecondFermentationComponent,
    ConditioningComponent,
    InputsComponent,
    FilteringComponent
  ]
})
export class AppModule { }
