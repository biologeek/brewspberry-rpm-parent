import { TestBed } from '@angular/core/testing';

import { RaspberryService } from './raspberry.service';

describe('RaspberryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RaspberryService = TestBed.get(RaspberryService);
    expect(service).toBeTruthy();
  });
});
