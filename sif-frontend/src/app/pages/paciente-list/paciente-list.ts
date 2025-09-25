import { Component, OnInit } from '@angular/core';
import { Paciente } from '../../models/paciente.model';
import { PacienteService } from '../../services/paciente';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Importe para usar o ngModel

@Component({
  selector: 'app-paciente-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule], // Adicione FormsModule
  templateUrl: './paciente-list.html',
  styleUrl: './paciente-list.css'
})
export class PacienteList implements OnInit {
  pacientes: Paciente[] = [];
  isLoading = true;
  termoBusca: string = '';
  filtros = {
    nome: '',
    cpf: '',
    nomeMae: ''
  };

  constructor(
    private pacienteService: PacienteService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.carregarPacientes();
  }

  carregarPacientes(): void {
    this.isLoading = true;
    this.pacienteService.getPacientes().subscribe({
      next: (data) => {
        this.pacientes = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar pacientes', err);
        this.isLoading = false;
      }
    });
  }

  // TODO: Implementar a busca no service e no backend
  buscar(): void {
    this.isLoading = true;
    this.pacienteService.buscarPacientes(this.filtros).subscribe({
      next: data => {
        this.pacientes = data;
        this.isLoading = false;
      },
      error: err => {
        console.error('Erro ao buscar pacientes', err);
        this.isLoading = false;
      }
    });
  }

  limparFiltros(): void {
    this.filtros = { nome: '', cpf: '', nomeMae: '' };
    this.carregarPacientes();
  }

  navegarParaNovoPaciente(): void {
    this.router.navigate(['/pacientes/novo']);
  }
}