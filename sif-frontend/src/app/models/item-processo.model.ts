import { Medicamento } from "./medicamento.model";

export interface ItemProcesso {
  id: number;
  quantidade: number;
  medicamento: Medicamento;
}