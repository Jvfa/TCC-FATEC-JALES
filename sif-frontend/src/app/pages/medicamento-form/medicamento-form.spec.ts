import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicamentoForm } from './medicamento-form';

describe('MedicamentoForm', () => {
  let component: MedicamentoForm;
  let fixture: ComponentFixture<MedicamentoForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicamentoForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicamentoForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
