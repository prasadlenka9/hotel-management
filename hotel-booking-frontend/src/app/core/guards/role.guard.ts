import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { inject } from '@angular/core';
import { TokenService } from '../services/token.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);

  const expectedRole = route.data['role'];
  const userRole = tokenService.getRole();

  if (userRole === expectedRole) {
    return true;
  }

  router.navigate(['/unauthorized']);
  return false;
};
