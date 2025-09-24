export interface Paciente {
  id: number;
  nome: string;
  nomeMae: string;
  dataNascimento: string; // O backend envia como string no JSON
  cpf: string;
  rg: string;
  cns: string;
  endereco: string;
  telefone: string;
  peso: number;
  altura: number;
  cor: string;
  statusCadastro: 'PRE_CADASTRO' | 'ATIVO' | 'INATIVO';
}