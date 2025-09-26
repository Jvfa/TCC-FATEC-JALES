import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PacienteService } from '../../services/paciente';
import { ProcessoService } from '../../services/processo';
import { Paciente } from '../../models/paciente.model';
import { Processo } from '../../models/processo.model';

@Component({
  selector: 'app-paciente-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './paciente-detail.html',
  styleUrl: './paciente-detail.css'
})
export class PacienteDetail implements OnInit {
  paciente: Paciente | null = null;
  processos: Processo[] = [];
  isLoading = true;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private pacienteService: PacienteService,
    private processoService: ProcessoService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const pacienteId = +idParam;
      this.carregarDadosDoPaciente(pacienteId);
    } else {
      this.errorMessage = "ID do paciente não fornecido na URL.";
      this.isLoading = false;
    }
  }

  carregarDadosDoPaciente(id: number): void {
    this.isLoading = true;
    this.pacienteService.getPacienteById(id).subscribe({
      next: (pacienteData) => {
        this.paciente = pacienteData;
        // Após carregar o paciente, busca os processos associados
        this.carregarProcessos(id);
      },
      error: (err) => {
        this.errorMessage = "Erro ao carregar os dados do paciente.";
        console.error(err);
        this.isLoading = false;
      }
    });
  }

  carregarProcessos(pacienteId: number): void {
    this.processoService.getProcessosByPacienteId(pacienteId).subscribe({
      next: (processosData) => {
        this.processos = processosData;
        this.isLoading = false; // Finaliza o loading após carregar tudo
      },
      error: (err) => {
        this.errorMessage = "Erro ao carregar os processos do paciente.";
        console.error(err);
        this.isLoading = false;
      }
    });
  }

  aprovarCadastro(): void {
    if (this.paciente && this.paciente.id) {
      this.pacienteService.aprovarCadastro(this.paciente.id).subscribe({
        next: (pacienteAtualizado) => {
          this.paciente = pacienteAtualizado; // Atualiza os dados na tela
          alert('Cadastro aprovado com sucesso!');
        },
        error: (err) => {
          alert('Falha ao aprovar o cadastro.');
          console.error(err);
        }
      });
    }
  }
}