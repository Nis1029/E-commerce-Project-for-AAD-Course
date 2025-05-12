import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  standalone: false,
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  totalOrders = 0;
  totalUsers = 0;
  totalProducts = 0;
  totalRevenue = 0;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchStats();
  }

  fetchStats() {
  const token = localStorage.getItem('authToken');
  console.log('🔐 Mevcut token:', token);
  if (!token) return;

  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  });

  this.http.get<any>('http://localhost:8080/api/admin/dashboard-stats', { headers })
    .subscribe({
      next: (res) => {
        this.totalOrders = res.totalOrders;
        this.totalUsers = res.totalUsers;
        this.totalProducts = res.totalProducts;
        this.totalRevenue = res.totalRevenue;
      },
      error: (err) => {
        console.error('❌ Dashboard veri çekilemedi:', err);
      }
    });
  }
}
