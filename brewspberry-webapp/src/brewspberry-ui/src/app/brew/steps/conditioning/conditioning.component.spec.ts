import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConditioningComponent } from './conditioning.component';

describe('ConditioningComponent', () => {
  let component: ConditioningComponent;
  let fixture: ComponentFixture<ConditioningComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConditioningComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConditioningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
