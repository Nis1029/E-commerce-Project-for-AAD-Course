import { Component, OnInit } from '@angular/core';
import { CartService, CartItem } from '../../services/cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  standalone: false,
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];

  constructor(
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.cart$.subscribe(items => {
      this.cartItems = items;
    });
  }

  increase(item: CartItem) {
    this.cartService.increaseQuantity(item.product.id);
  }

  decrease(item: CartItem) {
    this.cartService.decreaseQuantity(item.product.id);
  }

  remove(item: CartItem) {
    this.cartService.removeFromCart(item.product.id);
  }

  getTotalPrice() {
    return this.cartService.getTotalPrice();
  }

  // ✅ Sepet onayla butonu tıklanınca çağrılır
  completeOrder() {
    if (this.cartItems.length === 0) {
      alert('Sepetiniz boş!');
      return;
    }
    this.router.navigate(['/checkout']); // sadece yönlendirme
  }
}
