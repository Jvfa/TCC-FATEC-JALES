import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../services/auth';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const userRole = authService.getRole();
  const requiredRoles = route.data['roles'] as Array<string>;

  if (userRole && requiredRoles.includes(userRole)) {
    return true; // Permite o acesso
  }

  // Se não tiver permissão, redireciona para o dashboard
  router.navigate(['/dashboard']);
  return false;
};