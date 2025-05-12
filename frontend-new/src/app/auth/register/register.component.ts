import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  email = '';
  password = '';
  success = '';
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    this.authService.register(this.email, this.password).subscribe({
      next: () => {
        this.success = 'Kayıt başarılı! Giriş sayfasına yönlendiriliyorsunuz.';
        setTimeout(() => this.router.navigate(['/auth/login']), 1500);
      },
      error: () => {
        this.error = 'Kayıt başarısız!';
      }
    });
  }

}
