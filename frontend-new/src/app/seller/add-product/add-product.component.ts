import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-add-product',
  standalone: false,
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent {
  newProduct = {
    name: '',
    description: '',
    price: null,
    imageUrl: '',
    category: '',
    gender: '',
    stock: null
  };

  constructor(private http: HttpClient, private authService: AuthService) {}

  addProduct() {
    const token = this.authService.getToken();
    if (!token) {
      alert('Unauthorized');
      return;
    }

    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);

    this.http.post('http://localhost:8080/api/seller/products', this.newProduct, { headers })
      .subscribe({
        next: () => {
          alert('✅ Product added successfully');
          this.newProduct = {
            name: '',
            description: '',
            price: null,
            imageUrl: '',
            category: '',
            gender: '',
            stock: null
          };
        },
        error: (err) => {
          console.error('❌ Error while adding product:', err);
          alert('Something went wrong.');
        }
      });
  }
}
