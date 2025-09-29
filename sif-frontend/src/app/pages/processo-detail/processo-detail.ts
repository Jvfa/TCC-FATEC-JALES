import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Processo } from '../../models/processo.model';
import { ProcessoService } from '../../services/processo';
import { FormsModule } from '@angular/forms';
import { RetiradaRequestDTO } from '../../models/retirada.model';
import { RetiradaService } from '../../services/retirada';

@Component({
  selector: 'app-processo-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './processo-detail.html',
  styleUrl: './processo-detail.css'
})
export class ProcessoDetail implements OnInit {
  processo: Processo | null = null;
  isLoading = true;
  errorMessage: string | null = null;

  exibirFormularioRetirada = false;
  novaRetirada: Omit<RetiradaRequestDTO, 'processoId'> = {
    nomeRetirou: '',
    quantidade: 1
  };

  constructor(
    private route: ActivatedRoute,
    private processoService: ProcessoService,
    private retiradaService: RetiradaService
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const processoId = +idParam;
      this.carregarDadosDoProcesso(processoId);
    } else {
      this.errorMessage = "ID do processo não fornecido na URL.";
      this.isLoading = false;
    }
  }

  carregarDadosDoProcesso(id: number): void {
    this.isLoading = true;
    this.processoService.getProcessoById(id).subscribe({
      next: (data) => {
        this.processo = data;
        this.isLoading = false;
        console.log('Processo carregado:', this.processo);
      },
      error: (err) => {
        this.errorMessage = "Erro ao carregar os dados do processo.";
        console.error(err);
        this.isLoading = false;
      }
    });

  }

  registrarRetirada(): void {
    // PASSO 1: Vamos inspecionar o objeto 'processo' neste exato momento
    console.log('Tentando registrar retirada. Objeto do processo atual:', this.processo);

    // PASSO 2: Adicionamos uma verificação mais robusta para garantir que o ID existe
    if (!this.processo || !this.processo.id) {
      alert('Erro Crítico: ID do Processo não foi encontrado. Não é possível registrar a retirada.');
      console.error('O objeto this.processo está nulo ou sem ID:', this.processo);
      return;
    }

    if (!this.novaRetirada.nomeRetirou || this.novaRetirada.quantidade <= 0) {
      alert('Por favor, preencha todos os campos da retirada.');
      return;
    }

    const request: RetiradaRequestDTO = {
      processoId: this.processo.id,
      nomeRetirou: this.novaRetirada.nomeRetirou,
      quantidade: this.novaRetirada.quantidade
    };

    console.log('Enviando a seguinte requisição para o backend:', request);

    this.retiradaService.registrarRetirada(request).subscribe({
      next: () => {
        alert('Retirada registrada com sucesso!');
        this.exibirFormularioRetirada = false;
        this.novaRetirada = { nomeRetirou: '', quantidade: 1 };
        this.carregarDadosDoProcesso(this.processo!.id);
      },
      error: (err) => {
        alert('Erro ao registrar retirada.');
        console.error(err);
      }
    });
  }

}