import { Component, Input } from '@angular/core';
import { CartItem, CartService } from '../../services/cart.service';

@Component({
  selector: 'app-cart-item',
  standalone: false,
  templateUrl: './cart-item.component.html',
  styleUrl: './cart-item.component.css'
})
export class CartItemComponent {
  @Input() item!: CartItem;

  constructor(private cartService: CartService) { }

  increase() {
    this.cartService.increaseQuantity(this.item.product.id);
  }

  decrease() {
    this.cartService.decreaseQuantity(this.item.product.id);
  }

  remove() {
    this.cartService.removeFromCart(this.item.product.id);
  }

}
