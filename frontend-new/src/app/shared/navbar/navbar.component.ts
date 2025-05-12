import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: false,
  styleUrl: './navbar.component.css',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
  constructor(
    public authService: AuthService,
    private router: Router
  ) {}

    logout() {
    this.authService.logout();

    // Eğer sepeti de sıfırlamak istiyorsan:
    localStorage.removeItem('cart'); // veya this.cartService.clearCart();

    alert('Çıkış yapıldı ✅');
    this.router.navigate(['/']).then(() => {
      window.location.reload(); // DOM güncellenmiyorsa force refresh
    });
  }

  isAdmin(): boolean {
    return this.authService.getRole() === 'ADMIN';
  }

  isSeller():boolean{
    return this.authService.getRole() === 'SELLER';

  }
}
