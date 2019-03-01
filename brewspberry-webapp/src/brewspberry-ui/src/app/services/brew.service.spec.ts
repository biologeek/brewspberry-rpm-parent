import { TestBed } from '@angular/core/testing';

import { BrewService } from './brew.service';

describe('BrewService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BrewService = TestBed.get(BrewService);
    expect(service).toBeTruthy();
  });
});
