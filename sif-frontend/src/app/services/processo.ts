import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Processo, ProcessoRequestDTO } from '../models/processo.model';

@Injectable({
  providedIn: 'root'
})
export class ProcessoService {
  private readonly apiUrl = 'http://localhost:8090/api/processos';

  constructor(private http: HttpClient) { }

  getProcessosByPacienteId(pacienteId: number): Observable<Processo[]> {
    return this.http.get<Processo[]>(`${this.apiUrl}/paciente/${pacienteId}`);
  }

  getProcessoById(id: number): Observable<Processo> {
    return this.http.get<Processo>(`${this.apiUrl}/${id}`);
  }

  criarProcesso(request: ProcessoRequestDTO): Observable<Processo> {
    return this.http.post<Processo>(this.apiUrl, request);
  }
}