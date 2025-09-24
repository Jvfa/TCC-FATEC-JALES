import { Component, OnInit } from '@angular/core';
import { PacienteService } from '../../services/paciente'; // Importe o serviço
import { Paciente } from '../../models/paciente.model'; // Importe o modelo
import { CommonModule } from '@angular/common'; // Importe para usar *ngIf, *ngFor
import { RouterLink } from '@angular/router'; // Importe para usar o routerLink

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink // Adicione aos imports
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit { // Implemente a interface OnInit

  pacientesPendentes: Paciente[] = [];
  isLoading = true; // Variável para controlar o estado de carregamento

  // 1. Injete o PacienteService
  constructor(private pacienteService: PacienteService) {}

  // 2. ngOnInit é chamado automaticamente quando o componente é inicializado
  ngOnInit(): void {
    this.carregarPacientesPendentes();
  }

  // 3. Método para buscar os dados da API
  carregarPacientesPendentes(): void {
    this.isLoading = true; // Inicia o carregamento
    this.pacienteService.getCadastrosPendentes().subscribe({
      next: (data) => {
        this.pacientesPendentes = data; // Armazena os dados recebidos
        this.isLoading = false; // Finaliza o carregamento
        console.log('Pacientes pendentes carregados:', this.pacientesPendentes);
      },
      error: (err) => {
        console.error('Erro ao carregar pacientes pendentes', err);
        this.isLoading = false; // Finaliza o carregamento mesmo com erro
      }
    });
  }
}