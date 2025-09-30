import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Paciente } from '../../models/paciente.model';
import { PacienteService } from '../../services/paciente';
import { PacienteUpdateDTO } from '../../models/paciente-update.dto';
import { NgxMaskDirective } from 'ngx-mask';

@Component({
  selector: 'app-paciente-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, NgxMaskDirective],
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
  ) { }

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
      // Lógica de ATUALIZAÇÃO (CORRIGIDA)
      const updateDto: PacienteUpdateDTO = {
        nome: this.paciente.nome,
        nomeMae: this.paciente.nomeMae,
        dataNascimento: this.paciente.dataNascimento,
        rg: this.paciente.rg,
        cns: this.paciente.cns,
        telefone: this.paciente.telefone,
        peso: this.paciente.peso,
        altura: this.paciente.altura,
        cor: this.paciente.cor,
        // Novos campos de endereço adicionados
        cep: this.paciente.cep,
        cidade: this.paciente.cidade,
        bairro: this.paciente.bairro,
        rua: this.paciente.rua,
        numero: this.paciente.numero,
        complemento: this.paciente.complemento
      };

      this.pacienteService.atualizarPaciente(this.pacienteId, updateDto).subscribe({
        next: () => {
          alert('Paciente atualizado com sucesso!');
          this.router.navigate(['/pacientes']);
        },
        error: (err) => {
          console.error('Erro ao atualizar paciente', err);
          // Adicionar mensagem de erro para o usuário
        }
      });

    } else {
      // LÓGICA DE CRIAÇÃO (CORRIGIDA)
      const createData = {
        nome: this.paciente.nome,
        nomeMae: this.paciente.nomeMae,
        dataNascimento: this.paciente.dataNascimento,
        cpf: this.paciente.cpf,
        rg: this.paciente.rg,
        cns: this.paciente.cns,
        telefone: this.paciente.telefone,
        peso: this.paciente.peso,
        altura: this.paciente.altura,
        cor: this.paciente.cor,
        // Novos campos de endereço adicionados
        cep: this.paciente.cep,
        cidade: this.paciente.cidade,
        bairro: this.paciente.bairro,
        rua: this.paciente.rua,
        numero: this.paciente.numero,
        complemento: this.paciente.complemento
      };

      this.pacienteService.criarPreCadastro(createData).subscribe({
        next: () => {
          alert('Pré-cadastro realizado com sucesso!');
          this.router.navigate(['/pacientes']);
        },
        error: (err) => {
          console.error('Erro ao criar pré-cadastro', err);
          // Adicionar mensagem de erro para o usuário
        }
      });
    }
  }
}