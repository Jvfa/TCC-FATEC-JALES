import { Usuario } from "./usuario.model";

export interface Retirada {
  id: number;
  dataRetirada: string;
  nomeRetirou: string;
  quantidadeDispensada: number;
  atendente: Usuario;
}

// DTO para a requisição de criação de retirada
export interface RetiradaRequestDTO {
  processoId: number;
  atendenteId: number;
  nomeRetirou: string;
  quantidade: number;
}