// order-history.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-order-history',
  standalone:false,
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  orders: any[] = [];
  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken');
    if (!token) {
      this.errorMessage = 'Kullanıcı girişi yapılmamış.';
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get<any[]>('http://localhost:8080/api/orders/my-orders', { headers }).subscribe({
      next: (res) => {
        this.orders = res;
      },
      error: (err) => {
        console.error('❌ Siparişler alınamadı:', err);
        this.errorMessage = 'Siparişler yüklenemedi.';
      }
    });
  }
  getKargoProgress(status: string): string {
    switch (status) {
      case 'HAZIRLANIYOR':
        return '33%';
      case 'KARGODA':
        return '66%';
      case 'TESLIM_EDILDI':
        return '100%';
      default:
        return '0%';
    }
  }
}
