export interface Paciente {
  id: number;
  nome: string;
  nomeMae: string;
  dataNascimento: string; // O backend envia como string no JSON
  cpf: string;
  rg: string;
  cns: string;
  cep: string;
  cidade: string;
  bairro: string;
  rua: string;
  numero: string;
  complemento: string;
  telefone: string;
  peso: number;
  altura: number;
  cor: string;
  statusCadastro: 'PRE_CADASTRO' | 'ATIVO' | 'INATIVO';
}