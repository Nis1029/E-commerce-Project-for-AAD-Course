import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SellerDashboardComponent } from './seller-dashboard/seller-dashboard.component';
import { AddProductComponent } from './add-product/add-product.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { SalesReportComponent } from './sales-report/sales-report.component';
import { SellerGuard } from './seller.guard';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: SellerDashboardComponent,
    canActivate: [SellerGuard]
  },
  {
    path: 'add-product',
    component: AddProductComponent,
    canActivate: [SellerGuard]
  },
  {
    path: 'edit-product',
    component: EditProductComponent,
    canActivate: [SellerGuard]
  },
  {
    path: 'sales-report',
    component: SalesReportComponent,
    canActivate: [SellerGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SellerRoutingModule { }
