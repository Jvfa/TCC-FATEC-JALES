import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Retirada, RetiradaRequestDTO } from '../models/retirada.model';

@Injectable({
  providedIn: 'root'
})
export class RetiradaService {
  private readonly apiUrl = 'http://localhost:8080/api/retiradas';

  constructor(private http: HttpClient) { }

  /**
   * Envia uma requisição para registrar uma nova retirada de medicamento para um processo.
   * @param request O DTO contendo os IDs do processo, atendente e outras informações.
   * @returns Um Observable com a entidade Retirada que foi criada no banco.
   */
  registrarRetirada(request: RetiradaRequestDTO): Observable<Retirada> {
    return this.http.post<Retirada>(this.apiUrl, request);
  }
}