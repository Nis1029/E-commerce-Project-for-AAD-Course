import { ProductService } from './../../services/product.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-admin-product',
  standalone: false,
  templateUrl: './admin-product.component.html',
  styleUrl: './admin-product.component.css'
})
export class AdminProductComponent implements OnInit {
  products: any[] = [];
  newProduct = {
    name: '',
    description: '',
    price: null,
    imageUrl: '',
    category: '',
    gender: '',
    stock: null
  };


  constructor(private http: HttpClient, private authService: AuthService,private productService:ProductService) {}

  ngOnInit(): void {
    this.getAllProducts();
  }

  getAllProducts() {
    this.http.get<any[]>('http://localhost:8080/api/products')
      .subscribe(data => this.products = data);
  }
  addProduct() {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.authService.getToken());

    this.http.post('http://localhost:8080/api/admin/products', this.newProduct, { headers })
      .subscribe({
        next: () => {
          this.getAllProducts(); // Ürünleri yeniden listele
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
          console.error('Ürün ekleme hatası:', err);
        }
      });
  }

  deleteProduct(id: number) {
  if (confirm('Bu ürünü silmek istediğinizden emin misiniz?')) {
    this.productService.deleteProduct(id).subscribe({
      next: () => {
        this.getAllProducts(); // silindikten sonra listeyi yenile
      },
      error: (err) => {
        console.error('Silme hatası:', err);
        alert('Silinemedi! Ürün sipariş edilmiş olabilir.');
      }
    });
  }
}
}
