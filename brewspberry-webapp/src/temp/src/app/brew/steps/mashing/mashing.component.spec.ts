import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MashingComponent } from './mashing.component';

describe('MashingComponent', () => {
  let component: MashingComponent;
  let fixture: ComponentFixture<MashingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MashingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MashingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
