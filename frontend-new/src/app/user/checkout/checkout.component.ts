import { Component, OnInit } from '@angular/core';
import { CartItem, CartService } from '../../services/cart.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

declare var Stripe: any;

@Component({
  selector: 'app-checkout',
  standalone: false,
  templateUrl: './checkout.component.html'
})
export class CheckoutComponent implements OnInit {
  cartItems: CartItem[] = [];
  totalPrice: number = 0;

  name: string = '';
  address: string = '';
  city: string = '';
  postalCode: string = '';

  stripe: any;
  errorMessage: string = '';
  isProcessing: boolean = false;
  addressDetail: string = '';
  district: string = '';


  constructor(
    private cartService: CartService,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 1. Servisten al (öncelikli)
    this.cartService.cart$.subscribe(items => {
      this.cartItems = items;
      this.totalPrice = this.cartService.getTotalPrice();
      console.log('🛒 Checkout verisi (servis):', this.cartItems);
    });

    // 2. localStorage’dan fallback (her ihtimale karşı)
    if (this.cartItems.length === 0) {
      const stored = localStorage.getItem('cart');
      if (stored) {
        this.cartItems = JSON.parse(stored);
        this.totalPrice = this.cartItems.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
        console.log('📂 Checkout verisi (localStorage):', this.cartItems);
      }
    }

    this.stripe = Stripe('pk_test_51RMl5aRtFK4OWfynmFhedEqQU4tobOfAuKUJ4CcDAFsf5C0fttHxKQz9cFpi3HcUAMjmbWrSyzFj8F0cKH3YmoXm00RIKNfDfD');
  }

  pay() {

    if (this.cartItems.length === 0) {
      this.errorMessage = 'Sepetiniz boş, ödeme işlemi başlatılamaz.';
      return;
    }

    if (!this.name.trim() || !this.address.trim() || !this.city.trim() || !this.postalCode.trim()) {
      this.errorMessage = 'Adres bilgilerini eksiksiz giriniz.';
      return;
    }
    


    this.isProcessing = true;
    this.errorMessage = '';

    const items = this.cartItems.map(item => ({
      productId: item.product.id,
      quantity: item.quantity
    }));

    const token = localStorage.getItem('authToken');
    const headers = { Authorization: `Bearer ${token}` };

    localStorage.setItem('city', this.city);
    localStorage.setItem('district', this.district);
    localStorage.setItem('addressDetail', this.addressDetail);

    this.http.post<{ sessionId: string }>(
      'http://localhost:8080/api/payments/create-checkout-session',
      items,
      { headers }
    ).subscribe({
      next: res => {
        this.stripe.redirectToCheckout({
          sessionId: res.sessionId
        });
      },
      error: err => {
        this.errorMessage = 'Stripe bağlantısında hata oluştu.';
        console.error('❌ Stripe session error:', err);
      }
    });
  }
}
