import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private cartService: CartService,
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const category = params.get('category');
      if (category) {
        this.productService.getByCategory(category).subscribe(data => {
          this.products = data;
        });
      } else {
        this.productService.getAll().subscribe(data => {
          this.products = data;
        });
      }
    });
  }
  addToCart(product: Product) {
    this.cartService.addToCart(product);
    alert('✅ Product successfully added to cart!');
  }
}
