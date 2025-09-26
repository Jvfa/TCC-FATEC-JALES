import { ItemProcesso } from "./item-processo.model";
import { Retirada } from "./retirada.model";

export interface Processo {
  id: number;
  dataAbertura: string;
  mesInicioValidade: number;
  mesFimValidade: number;
  cid: string;
  status: 'EM_ABERTO' | 'ENCERRADO' | 'CANCELADO';
  observacoes: string;
  itensMedicamentos: ItemProcesso[];
  retiradas: Retirada[];
}

// DTO para a requisição de criação de processo
export interface ProcessoRequestDTO {
  pacienteId: number;
  processo: {
    dataAbertura: string;
    mesInicioValidade: number;
    mesFimValidade: number;
    cid: string;
    observacoes: string;
  };
  itens: {
    medicamentoId: number;
    quantidade: number;
  }[];
}