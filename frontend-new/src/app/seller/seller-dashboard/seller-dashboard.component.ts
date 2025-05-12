import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-seller-dashboard',
  standalone: false,
  templateUrl: './seller-dashboard.component.html',
  styleUrl: './seller-dashboard.component.css'
})
export class SellerDashboardComponent implements OnInit {
  totalSales = 0;
  totalProducts = 0;

  constructor(private http: HttpClient,private router: Router) {}

  ngOnInit(): void {
    this.fetchStats();
  }

  fetchStats() {
    const token = localStorage.getItem('authToken');
    if (!token) return;

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    this.http.get<any>('http://localhost:8080/api/seller/dashboard-stats', { headers })
      .subscribe({
        next: (res) => {
          this.totalSales = res.totalSales;
          this.totalProducts = res.totalProducts;
        },
        error: (err) => {
          console.error('❌ Satıcı dashboard verisi alınamadı:', err);
        }
      });
  }
  navigateTo(path: string) {
    this.router.navigate([`/seller/${path}`]);
  }
}
