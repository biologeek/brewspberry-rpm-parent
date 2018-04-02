import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ManageComponent } from './devices/manage/manage.component';
import { CreationFormComponent } from './devices/creation-form/creation-form.component';
import { ListComponent } from './brew/list/list.component';
import { CreateComponent } from './brew/create/create.component';
import { ParamsComponent } from './params/params.component';
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { DashboardComponent } from './dashboard/dashboard.component';


import { TooltipModule } from 'ngx-bootstrap/tooltip';
import {NgxToggleModule} from 'ngx-toggle';



export const router: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login', component: LoginComponent },
    { path: 'devices/manage', component: ManageComponent },
    { path: 'brew/list', component: ListComponent },
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
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(router),
    TooltipModule.forRoot(),
    NgxToggleModule
  ],
  providers: [],
  exports: [RouterModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
