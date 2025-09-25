import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth';

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
    return next(authReq);
  }

  // Se não houver token, passa a requisição original sem modificação
  return next(req);
};