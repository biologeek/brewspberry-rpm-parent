import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatchRequestPopupComponent } from './batch-request-popup.component';

describe('BatchRequestPopupComponent', () => {
  let component: BatchRequestPopupComponent;
  let fixture: ComponentFixture<BatchRequestPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatchRequestPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatchRequestPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
