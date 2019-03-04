import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FermentationComponent } from './fermentation.component';

describe('FermentationComponent', () => {
  let component: FermentationComponent;
  let fixture: ComponentFixture<FermentationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FermentationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FermentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
