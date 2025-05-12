import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css'],
  standalone: false
})
export class ProductItemComponent {
  @Input() item!: Product;
  @Output() add = new EventEmitter<Product>();

  onAdd() {
    this.add.emit(this.item);
  }
}
