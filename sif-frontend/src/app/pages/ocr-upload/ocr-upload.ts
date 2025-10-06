import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OcrService } from '../../services/ocr';
import { PacienteService } from '../../services/paciente';
import { Router } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';

@Component({
  selector: 'app-ocr-upload',
  standalone: true,
  imports: [CommonModule, FormsModule, NgxMaskDirective],
  templateUrl: './ocr-upload.html',
  styleUrls: ['./ocr-upload.css']
})
export class OcrUpload {
  imagemSelecionada: File | null = null;
  previewUrl: any = null;
  dadosExtraidos: any = null;
  isLoading = false;

  // Objeto paciente que será vinculado ao formulário de validação
  paciente: any = {};

  constructor(
    private ocrService: OcrService,
    private pacienteService: PacienteService,
    private router: Router
  ) { }

  onFileSelected(event: any): void {
    this.imagemSelecionada = event.target.files[0];
    if (this.imagemSelecionada) {
      const reader = new FileReader();
      reader.onload = e => this.previewUrl = reader.result;
      reader.readAsDataURL(this.imagemSelecionada);
      // Limpa os resultados anteriores ao selecionar uma nova imagem
      this.dadosExtraidos = null;
      this.paciente = {};
    }
  }

  analisarImagem(): void {
    if (!this.imagemSelecionada) {
      alert('Por favor, selecione uma imagem.');
      return;
    }
    this.isLoading = true;
    this.dadosExtraidos = null;
    this.paciente = {};

    this.ocrService.uploadImagem(this.imagemSelecionada).subscribe({
      next: (data) => {
        this.dadosExtraidos = data;
        this.mapearDadosParaFormulario(data);
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erro ao analisar imagem', err);
        alert('Falha ao analisar a imagem. Verifique as credenciais da AWS e o log do backend.');
        this.isLoading = false;
      }
    });
  }

  confirmarCadastro(): void {
    // Aqui usamos o objeto 'paciente' que o usuário pode ter corrigido no formulário
    this.pacienteService.criarPreCadastro(this.paciente).subscribe({
      next: () => {
        alert('Pré-cadastro a partir do OCR realizado com sucesso!');
        this.router.navigate(['/pacientes']);
      },
      error: (err) => {
        console.error('Erro ao salvar paciente', err);
        alert('Falha ao salvar o cadastro. Verifique os dados preenchidos.');
      }
    });
  }

  private mapearDadosParaFormulario(data: any): void {
    console.log("Dados brutos do Textract:", data);

    // --- Mapeamento para Dados Pessoais ---
    this.paciente.nome = this.findValueForKey('Nome');

    // Tratamento especial para o nome da mãe, que veio junto com a data
    let nomeMaeCompleto = this.findValueForKey('Mãe');
    if (nomeMaeCompleto) {
      // Remove qualquer sequência de números (a data) do final da string
      this.paciente.nomeMae = nomeMaeCompleto.replace(/\s*\d+$/, '').trim();
    }

    // Tenta encontrar a data de nascimento por 'DN' ou extrai do campo da mãe se falhar
    let dn = this.findValueForKey('DN');
    if (!dn && nomeMaeCompleto.match(/\d+$/)) {
      dn = nomeMaeCompleto.match(/\d+$/)![0];
    }
    this.paciente.dataNascimento = this.formatDate(dn);

    this.paciente.cpf = this.findValueForKey('CPF');
    this.paciente.rg = this.findValueForKey('RG');
    this.paciente.cns = this.findValueForKey('CNS');
    this.paciente.telefone = this.findValueForKey('Tel'); // 'Tel' é mais provável de ser lido que 'Telefone'
    this.paciente.cor = this.findValueForKey('Cor');

    // --- Mapeamento para Dados Físicos ---
    this.paciente.peso = this.formatNumber(this.findValueForKey('Peso'));
    this.paciente.altura = this.formatNumber(this.findValueForKey('Alt')); // 'Alt' é mais provável

    // --- Mapeamento para Endereço ---
    // O endereço pode vir em uma linha só, então mapeamos para o campo principal (rua)
    // e o usuário pode separar os outros campos manualmente na tela de validação.
    const enderecoCompleto = this.findValueForKey('End');
    this.paciente.rua = enderecoCompleto;
    this.paciente.cep = '';
    this.paciente.bairro = '';
    this.paciente.cidade = '';
    this.paciente.numero = '';
  }

  private findValueForKey(keyPart: string): string {
    if (!this.dadosExtraidos) return '';
    const keyFound = Object.keys(this.dadosExtraidos).find(k => k.toLowerCase().includes(keyPart.toLowerCase()));
    return keyFound ? this.dadosExtraidos[keyFound] : '';
  }

  private formatDate(dateStr: string): string {
    if (!dateStr || !/^\d{2}\/\d{2}\/\d{2,4}$/.test(dateStr)) return '';
    const parts = dateStr.split('/');
    if (parts.length !== 3) return '';
    const year = parts[2].length === 2 ? `20${parts[2]}` : parts[2];
    const month = parts[1].padStart(2, '0');
    const day = parts[0].padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  private formatNumber(numStr: string): number | null {
    if (!numStr) return null;
    const match = numStr.match(/[\d,.]+/);
    if (!match) return null;
    return parseFloat(match[0].replace(',', '.'));
  }
}