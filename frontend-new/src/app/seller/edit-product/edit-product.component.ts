import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-edit-product',
  standalone: false,
  templateUrl: './edit-product.component.html',
  styleUrl: './edit-product.component.css'
})
export class EditProductComponent implements OnInit {
  products: Product[] = [];
  stockUpdates: { [id: number]: number } = {};
  priceUpdates: { [id: number]: number } = {};
  loading = true;
  error = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getAll().subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Products could not be loaded.';
        this.loading = false;
      }
    });
  }

  updateStock(product: Product): void {
    const newStock = this.stockUpdates[product.id!];
    if (newStock == null || newStock < 0) {
      alert('Please enter a valid stock value.');
      return;
    }

    const updated = { ...product, stock: newStock };
    this.productService.updateProduct(product.id!, updated).subscribe({
      next: () => {
        alert('Stock updated!');
        this.loadProducts();
      },
      error: () => alert('Stock update failed!')
    });
  }

  updatePrice(product: Product): void {
    const newPrice = this.priceUpdates[product.id!];
    if (newPrice == null || newPrice <= 0) {
      alert('Please enter a valid price.');
      return;
    }

    const updated = { ...product, price: newPrice };
    this.productService.updateProduct(product.id!, updated).subscribe({
      next: () => {
        alert('Price updated!');
        this.loadProducts();
      },
      error: () => alert('Price update failed!')
    });
  }

  deleteProduct(id: number): void {
    if (!confirm('Are you sure you want to delete this product?')) return;

    this.productService.deleteProduct(id).subscribe({
      next: () => {
        alert('Product deleted.');
        this.loadProducts();
      },
      error: () => alert('Delete failed. Maybe the product was ordered.')
    });
  }
}
