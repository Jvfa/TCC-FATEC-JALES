import { TestBed } from '@angular/core/testing';

import { Retirada } from './retirada';

describe('Retirada', () => {
  let service: Retirada;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Retirada);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
