import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Medicamento } from '../../models/medicamento.model';
import { MedicamentoService } from '../../services/medicamento';

@Component({
  selector: 'app-medicamento-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './medicamento-form.html',
  styleUrls: ['./medicamento-form.css'] // Corrigido para styleUrls
})
export class MedicamentoForm implements OnInit {
  medicamento: Omit<Medicamento, 'id'> & { id?: number } = {
    nome: '',
    dosagem: '',
    formaFarmaceutica: ''
  };
  isEditMode = false;
  private medicamentoId: number | null = null;

  constructor(
    private medicamentoService: MedicamentoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.medicamentoId = +idParam;
      this.medicamentoService.getMedicamentoById(this.medicamentoId).subscribe(data => {
        this.medicamento = data;
      });
    }
  }

  onSubmit(): void {
    if (this.isEditMode && this.medicamentoId) {
      this.medicamentoService.atualizarMedicamento(this.medicamentoId, this.medicamento as Medicamento).subscribe(() => {
        alert('Medicamento atualizado com sucesso!');
        this.router.navigate(['/medicamentos']);
      });
    } else {
      this.medicamentoService.cadastrarMedicamento(this.medicamento).subscribe(() => {
        alert('Medicamento cadastrado com sucesso!');
        this.router.navigate(['/medicamentos']);
      });
    }
  }
}