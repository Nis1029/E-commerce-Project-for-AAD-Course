import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';



import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminOrderComponent } from './admin-order/admin-order.component';
import { AdminProductComponent } from './admin-product/admin-product.component';

import { AdminUserComponent } from './admin-user/admin-user.component';
import { FormsModule } from '@angular/forms';




@NgModule({
  declarations: [

    AdminDashboardComponent,
     AdminOrderComponent,
     AdminProductComponent,

     AdminUserComponent,

  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule
  ]
})
export class AdminModule {}
