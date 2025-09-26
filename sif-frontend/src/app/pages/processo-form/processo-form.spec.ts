import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessoForm } from './processo-form';

describe('ProcessoForm', () => {
  let component: ProcessoForm;
  let fixture: ComponentFixture<ProcessoForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessoForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProcessoForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
