
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth/auth.guard';



import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminGuard } from './admin.guard';
import { AdminProductComponent } from './admin-product/admin-product.component';
import { AdminOrderComponent } from './admin-order/admin-order.component';
import { AdminUserComponent } from './admin-user/admin-user.component';




const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  
  {
    path: 'dashboard',
    component: AdminDashboardComponent,
    canActivate: [AdminGuard] // sadece admin girebilir
  },
  {
    path: 'orders',
    component: AdminOrderComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'products',
    component: AdminProductComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'users',
    component: AdminUserComponent,
    canActivate: [AdminGuard]
  }

];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
