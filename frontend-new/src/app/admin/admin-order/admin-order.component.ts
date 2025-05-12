import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

interface Order {
  id: number;
  address: string;
  createdAt: string;
  totalPrice: number;
  status: string;
  kargoDurumu: string;

  user: {
    id: number;
    email: string;
  };

  items: {
    product: {
      name: string;
      description: string;
      price: number;
    };
    quantity: number;
    totalPrice: number;
  }[];
}
@Component({
  selector: 'app-admin-order',
  standalone: false,
  templateUrl: './admin-order.component.html',
  styleUrl: './admin-order.component.css'
})
export class AdminOrderComponent implements OnInit {
  orders: Order[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

    this.http.get<Order[]>('http://localhost:8080/api/orders/all', { headers })
      .subscribe({
        next: (res) => this.orders = res,
        error: (err) => console.error('Admin order fetch error:', err)
      });
  }
  getStatusBadge(status: string): string {
    switch (status) {
      case 'PREPARING':
        return 'bg-warning text-dark';
      case 'SHIPPED':
        return 'bg-primary';
      case 'DELIVERED':
        return 'bg-success';
      case 'CANCELLED':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }
}

