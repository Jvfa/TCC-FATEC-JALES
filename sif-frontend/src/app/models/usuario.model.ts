export interface Usuario {
  id: number;
  nome: string;
  login: string;
  perfil: 'ATENDENTE' | 'FARMACEUTICO' | 'ADMINISTRADOR';
}