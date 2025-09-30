import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';

export const authGuard: CanActivateFn = (route, state) => {
  // Injeta o AuthService e o Router
  const authService = inject(AuthService);
  const router = inject(Router);

  // Usa o método que já temos para verificar se o usuário está logado
  if (authService.isLoggedIn()) {
    return true; // Se estiver logado, permite o acesso à rota
  } else {
    // Se não estiver logado, redireciona para a página de login
    console.warn('Acesso negado: usuário não autenticado. Redirecionando para /login');
    router.navigate(['/login']);
    return false; // E bloqueia o acesso à rota que o usuário tentou acessar
  }
};