import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Medicamento } from '../../models/medicamento.model';
import { MedicamentoService } from '../../services/medicamento';

@Component({
  selector: 'app-medicamento-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './medicamento-list.html',
  styleUrl: './medicamento-list.css'
})
export class MedicamentoList implements OnInit {
  medicamentos: Medicamento[] = [];
  isLoading = true;

  constructor(
    private medicamentoService: MedicamentoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarMedicamentos();
  }

  carregarMedicamentos(): void {
    this.isLoading = true;
    this.medicamentoService.getMedicamentos().subscribe({
      next: (data) => {
        this.medicamentos = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar medicamentos', err);
        this.isLoading = false;
        // Opcional: Adicionar mensagem de erro para o usuário
      }
    });
  }

  deletarMedicamento(id: number): void {
    // Adiciona uma confirmação antes de deletar
    if (confirm('Tem certeza que deseja deletar este medicamento?')) {
      this.medicamentoService.deletarMedicamento(id).subscribe({
        next: () => {
          alert('Medicamento deletado com sucesso!');
          // Recarrega a lista para refletir a exclusão
          this.carregarMedicamentos();
        },
        error: (err) => {
          alert('Erro ao deletar medicamento.');
          console.error(err);
        }
      });
    }
  }

  navegarParaNovo(): void {
    this.router.navigate(['/medicamentos/novo']);
  }
}