import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

import { LoginRequest } from '../../shared/models/login-request.model';
import { RegisterRequest } from '../../shared/models/register-request.model';
import { AuthResponse } from '../../shared/models/auth-response.model';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = environment.apiUrl + '/auth';

  constructor(
    private http: HttpClient,
    private tokenService: TokenService
  ) {}

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.apiUrl}/login`,
      request
    ).pipe(
      tap(response => {
        this.tokenService.setToken(response.token);
        this.tokenService.setRole(response.role);
      })
    );
  }

  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.apiUrl}/register`,
      request
    ).pipe(
      tap(response => {
        this.tokenService.setToken(response.token);
        this.tokenService.setRole(response.role);
      })
    );
  }

  logout(): void {
    this.tokenService.clear();
  }
}
