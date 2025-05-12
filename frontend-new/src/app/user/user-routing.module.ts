import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { CartComponent } from './cart/cart.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';

import { HomeComponent } from './home/home.component';
import { SuccessComponent } from './success/success.component';


const routes: Routes = [
  { path: 'products/:category', component: ProductListComponent },
  { path: 'cart', component: CartComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'order-history', component: OrderHistoryComponent },
  { path: 'order-confirmation', component: OrderConfirmationComponent },
  { path: 'product/:id', component: ProductDetailComponent },
  { path: '', component: HomeComponent },
  { path: 'success', component: SuccessComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
