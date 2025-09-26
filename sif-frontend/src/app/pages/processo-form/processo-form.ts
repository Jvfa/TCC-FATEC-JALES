import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Medicamento } from '../../models/medicamento.model';
import { MedicamentoService } from '../../services/medicamento'; // Garanta que o caminho do arquivo esteja correto
import { ProcessoService } from '../../services/processo'; // Garanta que o caminho do arquivo esteja correto
import { ProcessoRequestDTO } from '../../models/processo.model';

@Component({
  selector: 'app-processo-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  // CORRIGINDO O NOME DO ARQUIVO DE TEMPLATE E ESTILO
  templateUrl: './processo-form.html',
  styleUrl: './processo-form.css'
})
// CORRIGINDO O NOME DA CLASSE
export class ProcessoForm implements OnInit {
  processoForm: FormGroup;
  pacienteId!: number;
  medicamentosDisponiveis: Medicamento[] = [];
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private medicamentoService: MedicamentoService,
    private processoService: ProcessoService
  ) {
    this.processoForm = this.fb.group({
      mesInicioValidade: ['', [Validators.required, Validators.min(1), Validators.max(12)]],
      mesFimValidade: ['', [Validators.required, Validators.min(1), Validators.max(12)]],
      cid: ['', Validators.required],
      observacoes: [''],
      itens: this.fb.array([this.novoItem()], Validators.required)
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('pacienteId');
    if (idParam) {
      this.pacienteId = +idParam;
    }

    this.medicamentoService.getMedicamentos().subscribe(data => {
      this.medicamentosDisponiveis = data;
    });
  }

  get itens(): FormArray {
    return this.processoForm.get('itens') as FormArray;
  }

  novoItem(): FormGroup {
    return this.fb.group({
      medicamentoId: ['', Validators.required],
      quantidade: ['', [Validators.required, Validators.min(1)]]
    });
  }

  adicionarItem(): void {
    this.itens.push(this.novoItem());
  }

  removerItem(index: number): void {
    if (this.itens.length > 1) {
      this.itens.removeAt(index);
    }
  }

  onSubmit(): void {
    console.log('--- PASSO 1: Método onSubmit foi chamado. ---');
    this.errorMessage = null;

    if (this.processoForm.invalid) {
      console.error('--- FALHA: O formulário foi considerado inválido. ---');
      console.log('Estado do formulário:', this.processoForm.status);
      console.log('Valores do formulário:', this.processoForm.value);
      console.log('Erros detalhados:', this.processoForm.controls);

      this.errorMessage = "Por favor, preencha todos os campos obrigatórios corretamente.";
      return;
    }

    console.log('--- PASSO 2: O formulário é válido. Construindo a requisição... ---');
    const formValue = this.processoForm.value;
    const request: ProcessoRequestDTO = {
      pacienteId: this.pacienteId,
      processo: {
        dataAbertura: new Date().toISOString().split('T')[0],
        mesInicioValidade: formValue.mesInicioValidade,
        mesFimValidade: formValue.mesFimValidade,
        cid: formValue.cid,
        observacoes: formValue.observacoes
      },
      itens: formValue.itens
    };

    console.log('--- PASSO 3: Enviando a seguinte requisição para o serviço:', request);

    this.processoService.criarProcesso(request).subscribe({
      next: () => {
        console.log('--- SUCESSO: Processo criado com sucesso! ---');
        alert('Novo processo criado com sucesso!');
        this.router.navigate(['/pacientes', this.pacienteId]);
      },
      error: (err) => {
        console.error('--- FALHA: Ocorreu um erro na chamada da API. ---', err);
        this.errorMessage = "Erro ao criar o processo. Tente novamente.";
      }
    });
  }

  public testarClique(): void {
  console.log("!!! O BOTÃO FOI CLICADO E O MÉTODO testarClique() FOI EXECUTADO !!!");
  alert("O evento de clique está funcionando!");
}
}