import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service'; // ← DOĞRU YOL

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean | UrlTree {
  const role = this.authService.getRole()?.toUpperCase();
  return role === 'ADMIN' ? true : this.router.parseUrl('/auth/login');
}
}
