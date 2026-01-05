import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [

  {
    path: 'user',
    canActivate: [authGuard],
    loadComponent: () =>
        import('./user/bookings/bookings.component')
        .then(m => m.BookingsComponent)
  },

  {
    path: 'admin',
    canActivate: [authGuard, roleGuard],
    data: { role: 'ADMIN' },
    loadComponent: () =>
      import('./admin/dashboard/dashboard.component')
        .then(m => m.DashboardComponent)
  },

  {
    path: 'login',
    loadComponent: () =>
      import('./auth/login/login.component')
        .then(m => m.LoginComponent)
  },

  {
    path: 'unauthorized',
    loadComponent: () =>
      import('./shared/components/unauthorized.component')
        .then(m => m.UnauthorizedComponent)
  }
];
