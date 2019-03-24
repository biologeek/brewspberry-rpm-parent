import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityChartPopupComponent } from './activity-chart-popup.component';

describe('ActivityChartPopupComponent', () => {
  let component: ActivityChartPopupComponent;
  let fixture: ComponentFixture<ActivityChartPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivityChartPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityChartPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
