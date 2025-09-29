import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessoDetail } from './processo-detail';

describe('ProcessoDetail', () => {
  let component: ProcessoDetail;
  let fixture: ComponentFixture<ProcessoDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessoDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProcessoDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
