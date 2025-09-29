import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Paciente } from '../models/paciente.model';
import { PacienteUpdateDTO } from '../models/paciente-update.dto';

@Injectable({
  providedIn: 'root'
})
export class PacienteService {
  private readonly apiUrl = 'http://localhost:8090/api/pacientes';

  constructor(private http: HttpClient) { }

  getPacientes(): Observable<Paciente[]> {
    return this.http.get<Paciente[]>(this.apiUrl);
  }

  getPacienteById(id: number): Observable<Paciente> {
    return this.http.get<Paciente>(`${this.apiUrl}/${id}`);
  }

  getCadastrosPendentes(): Observable<Paciente[]> {
    return this.http.get<Paciente[]>(`${this.apiUrl}/pendentes`);
  }

  criarPreCadastro(pacienteData: Partial<Paciente>): Observable<Paciente> {
    return this.http.post<Paciente>(this.apiUrl, pacienteData);
  }

  atualizarPaciente(id: number, dto: PacienteUpdateDTO): Observable<Paciente> {
    return this.http.put<Paciente>(`${this.apiUrl}/${id}`, dto);
  }

  aprovarCadastro(id: number): Observable<Paciente> {
    return this.http.put<Paciente>(`${this.apiUrl}/${id}/aprovar`, {});
  }

  buscarPacientes(filtros: any): Observable<Paciente[]> {
    // Constrói os parâmetros de URL, removendo chaves com valores nulos ou vazios
    const params = Object.entries(filtros)
      .filter(([key, value]) => value !== null && value !== '')
      .reduce((acc, [key, value]) => ({ ...acc, [key]: value }), {});

    return this.http.get<Paciente[]>(this.apiUrl, { params });
  }
}