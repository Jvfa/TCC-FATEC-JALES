import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Injeta o AuthService para pegar o token
  const authService = inject(AuthService);
  const authToken = authService.getToken();

  // Verifica se o token existe
  if (authToken) {
    // Clona a requisição original e adiciona o cabeçalho de autorização
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });
    // Passa a requisição clonada (com o header) para a próxima etapa
    return next(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        // Se o erro for 403 (Forbidden) ou 401 (Unauthorized),
        // o token é inválido, então fazemos o logout.
        if (error.status === 403 || error.status === 401) {
          console.log("Token inválido ou expirado. Fazendo logout automático.");
          authService.logout();
        }
        return throwError(() => error);
      })
    );
  }

  // Se não houver token, passa a requisição original sem modificação
  return next(req);
};