import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecondFermentationComponent } from './second-fermentation.component';

describe('SecondFermentationComponent', () => {
  let component: SecondFermentationComponent;
  let fixture: ComponentFixture<SecondFermentationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecondFermentationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecondFermentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
