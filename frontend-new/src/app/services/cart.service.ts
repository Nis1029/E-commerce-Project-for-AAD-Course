import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
  gender: string;
}

export interface CartItem {
  product: Product;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems: CartItem[] = [];
  private cartSubject = new BehaviorSubject<CartItem[]>([]);
  cart$ = this.cartSubject.asObservable();

  constructor() {
    console.log('📦 CartService başlatıldı!');
    const storedCart = localStorage.getItem('cart');
    this.cartItems = storedCart ? JSON.parse(storedCart) : [];
    this.cartSubject.next([...this.cartItems]);
  }

  private updateCart() {
    localStorage.setItem('cart', JSON.stringify(this.cartItems));
    this.cartSubject.next([...this.cartItems]);
  }

  addToCart(product: Product) {
    const existingItem = this.cartItems.find(item => item.product.id === product.id);
    if (existingItem) {
      existingItem.quantity++;
    } else {
      this.cartItems.push({ product, quantity: 1 });
    }
    this.updateCart();
  }

  increaseQuantity(productId: number) {
    const item = this.cartItems.find(item => item.product.id === productId);
    if (item) {
      item.quantity++;
      this.updateCart();
    }
  }

  decreaseQuantity(productId: number) {
    const item = this.cartItems.find(item => item.product.id === productId);
    if (!item) return;

    item.quantity > 1 ? item.quantity-- : this.removeFromCart(productId);
    this.updateCart();
  }

  removeFromCart(productId: number) {
    this.cartItems = this.cartItems.filter(item => item.product.id !== productId);
    this.updateCart();
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }

  clearCart() {
    this.cartItems = [];
    this.updateCart();
  }

  getCartItems(): CartItem[] {
    const items = [...this.cartItems];
    console.log('📦 getCartItems çağrıldı:', items);
    return items;
  }
}
