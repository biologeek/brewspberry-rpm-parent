import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LauteringComponent } from './lautering.component';

describe('LauteringComponent', () => {
  let component: LauteringComponent;
  let fixture: ComponentFixture<LauteringComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LauteringComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LauteringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
