import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
export interface User {
  id: number;
  email: string;
  role: string;
  isBanned: boolean; // ← bunu ekle
}

@Component({
  selector: 'app-admin-user',
  standalone: false,
  templateUrl: './admin-user.component.html',
  styleUrl: './admin-user.component.css'
})
export class AdminUserComponent implements OnInit {
  users: User[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    this.http.get<User[]>('http://localhost:8080/api/admin/users', { headers })
      .subscribe(res => this.users = res);
  }

  toggleBan(user: User): void {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    this.http.put(`http://localhost:8080/api/admin/users/${user.id}/toggle-ban`, {}, { headers })
      .subscribe(() => {
        user.isBanned = !user.isBanned;
      });
  }
}

