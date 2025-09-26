import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { LoginRequestDTO, LoginResponseDTO } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:8090/api/auth';
  private readonly TOKEN_KEY = 'sif_auth_token';

  constructor(private http: HttpClient, private router: Router) { }

  login(credentials: LoginRequestDTO): Observable<LoginResponseDTO> {
    return this.http.post<LoginResponseDTO>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        // Salva o token no localStorage após o login bem-sucedido
        localStorage.setItem(this.TOKEN_KEY, response.token);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken(); // Retorna true se o token existir
  }

  getRole(): string | null {
    const token = this.getToken();
    if (token) {
      // Decodifica a parte "payload" do token JWT
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role;
    }
    return null;
  }
}