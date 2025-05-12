import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-login',
  standalone:false,
  templateUrl: './login.component.html'
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (res) => {
        this.authService.saveToken(res.token);

        // 🔍 Token içinden rolü çek
        const tokenPayload = JSON.parse(atob(res.token.split('.')[1]));
        const backendRole = tokenPayload.role;

        // 🎯 Frontend için sadeleştirilmiş rol
        let role = 'USER';
        if (backendRole === 'ROLE_ADMIN') role = 'ADMIN';
        else if (backendRole === 'ROLE_SELLER') role = 'SELLER';

        this.authService.saveRole(role);

        // 🧠 Rol bazlı mesaj
        if (role === 'ADMIN') {
          alert('🔐 Admin olarak giriş yapıldı.');
        } else if (role === 'SELLER') {
          alert('🛒 Satıcı olarak giriş yapıldı.');
        } else {
          alert('✅ Giriş başarılı.');
        }

        this.router.navigate(['/']);
      },
      error: () => {
        this.error = 'E-posta veya şifre hatalı.';
      }
    });
  }
}
