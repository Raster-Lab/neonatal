import { Injectable, signal } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';

export interface User {
  username: string;
  token: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly authenticated = signal(false);
  private currentUser: User | null = null;

  login(username: string, password: string): Observable<User> {
    if (username && password) {
      const user: User = { username, token: 'mock-token' };
      this.currentUser = user;
      this.authenticated.set(true);
      return of(user);
    }
    return throwError(() => new Error('Invalid credentials'));
  }

  logout(): void {
    this.currentUser = null;
    this.authenticated.set(false);
  }

  isAuthenticated(): boolean {
    return this.authenticated();
  }

  getCurrentUser(): User | null {
    return this.currentUser;
  }
}
