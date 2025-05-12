import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-success',
  standalone: false,
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  constructor(private http: HttpClient, private cartService: CartService ) {}

  ngOnInit(): void {
    console.log('✅ SuccessComponent yüklendi!');

    const token = localStorage.getItem('authToken');


    const city = localStorage.getItem('city') || '';
    const district = localStorage.getItem('district') || '';
    const addressDetail = localStorage.getItem('addressDetail') || '';
    const fullAddress = `${addressDetail}, ${district}, ${city}`.trim();

    if (!token || !fullAddress.trim()) {
      console.warn('❗ Gerekli bilgiler eksik, sipariş gönderilmedi.');
      return;
    }

    const cartItems = this.cartService.getCartItems();
    if (cartItems.length === 0) {
      console.warn('❗ Sepet boş, sipariş gönderilmedi.');
      return;
    }

    const items = cartItems.map(item => ({
      productId: item.product.id,
      quantity: item.quantity
    }));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    const payload = {
      address: fullAddress,
      items: items
    };

    console.log('📦 Sipariş gönderiliyor:', payload);

    this.http.post('http://localhost:8080/api/orders/confirm', payload, { headers, responseType: 'text' })
    .subscribe({
      next: (res) => {
        console.log('✅ Sipariş başarıyla kaydedildi:', res);

        this.cartService.clearCart();                  // servis sepeti
        localStorage.removeItem('cart');               // localStorage sepeti
        localStorage.removeItem('city');               // adres verileri (isteğe bağlı)
        localStorage.removeItem('district');
        localStorage.removeItem('addressDetail');
      },
      error: (err) => {
        console.error('❌ Sipariş kaydedilemedi:', err);
      }
    });
  }
}
