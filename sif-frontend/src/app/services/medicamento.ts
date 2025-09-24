import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medicamento } from '../models/medicamento.model';

@Injectable({
  providedIn: 'root'
})
export class MedicamentoService {
  private readonly apiUrl = 'http://localhost:8080/api/medicamentos';

  constructor(private http: HttpClient) { }

  /**
   * Busca a lista completa de medicamentos cadastrados.
   * Essencial para preencher listas de seleção no formulário de processo.
   * @returns Um Observable com a lista de medicamentos.
   */
  getMedicamentos(): Observable<Medicamento[]> {
    return this.http.get<Medicamento[]>(this.apiUrl);
  }

  /*
   // Os métodos abaixo seriam para uma tela de administração de medicamentos

   getMedicamentoById(id: number): Observable<Medicamento> {
     return this.http.get<Medicamento>(`${this.apiUrl}/${id}`);
   }

   cadastrarMedicamento(medicamento: Omit<Medicamento, 'id'>): Observable<Medicamento> {
     return this.http.post<Medicamento>(this.apiUrl, medicamento);
   }

   atualizarMedicamento(id: number, medicamento: Medicamento): Observable<Medicamento> {
     return this.http.put<Medicamento>(`${this.apiUrl}/${id}`, medicamento);
   }

   deletarMedicamento(id: number): Observable<void> {
     return this.http.delete<void>(`${this.apiUrl}/${id}`);
   }
  */
}