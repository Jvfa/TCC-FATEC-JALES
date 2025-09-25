import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Paciente } from '../../models/paciente.model';
import { PacienteService } from '../../services/paciente';

@Component({
  selector: 'app-paciente-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './paciente-form.html',
  styleUrl: './paciente-form.css'
})
export class PacienteForm implements OnInit {
  paciente: any = {}; // Usamos 'any' para o formulário inicial
  isEditMode = false;
  private pacienteId: number | null = null;

  constructor(
    private pacienteService: PacienteService,
    private router: Router,
    private route: ActivatedRoute // Para ler o ID da URL
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.pacienteId = +id; // O '+' converte a string para número
      this.pacienteService.getPacienteById(this.pacienteId).subscribe(data => {
        this.paciente = data;
      });
    }
  }

  onSubmit(): void {
    if (this.isEditMode && this.pacienteId) {
      this.pacienteService.atualizarPaciente(this.pacienteId, this.paciente).subscribe(() => {
        alert('Paciente atualizado com sucesso!');
        this.router.navigate(['/pacientes']);
      });
    } else {
      this.pacienteService.criarPreCadastro(this.paciente).subscribe(() => {
        alert('Pré-cadastro realizado com sucesso!');
        this.router.navigate(['/pacientes']);
      });
    }
  }
}