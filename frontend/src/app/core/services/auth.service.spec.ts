import { TestBed } from '@angular/core/testing';
import { AuthService, User } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should not be authenticated initially', () => {
    expect(service.isAuthenticated()).toBeFalse();
  });

  it('should return null user initially', () => {
    expect(service.getCurrentUser()).toBeNull();
  });

  it('should login successfully with valid credentials', (done) => {
    service.login('admin', 'password').subscribe((user: User) => {
      expect(user.username).toBe('admin');
      expect(user.token).toBe('mock-token');
      expect(service.isAuthenticated()).toBeTrue();
      expect(service.getCurrentUser()).toEqual(user);
      done();
    });
  });

  it('should fail login with empty username', (done) => {
    service.login('', 'password').subscribe({
      error: (err: Error) => {
        expect(err.message).toBe('Invalid credentials');
        expect(service.isAuthenticated()).toBeFalse();
        done();
      },
    });
  });

  it('should fail login with empty password', (done) => {
    service.login('admin', '').subscribe({
      error: (err: Error) => {
        expect(err.message).toBe('Invalid credentials');
        expect(service.isAuthenticated()).toBeFalse();
        done();
      },
    });
  });

  it('should logout successfully', () => {
    service.login('admin', 'password').subscribe();
    service.logout();
    expect(service.isAuthenticated()).toBeFalse();
    expect(service.getCurrentUser()).toBeNull();
  });
});
