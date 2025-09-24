import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Paciente } from '../models/paciente.model';

@Injectable({
  providedIn: 'root'
})
export class PacienteService {
  private readonly apiUrl = 'http://localhost:8080/api/pacientes';

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

  criarPreCadastro(paciente: Omit<Paciente, 'id' | 'statusCadastro'>): Observable<Paciente> {
    return this.http.post<Paciente>(this.apiUrl, paciente);
  }

  atualizarPaciente(id: number, paciente: Paciente): Observable<Paciente> {
    return this.http.put<Paciente>(`${this.apiUrl}/${id}`, paciente);
  }

  aprovarCadastro(id: number): Observable<Paciente> {
    return this.http.put<Paciente>(`${this.apiUrl}/${id}/aprovar`, {});
  }
}