import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SellerRoutingModule } from './seller-routing.module';
import { FormsModule } from '@angular/forms';
import { SellerDashboardComponent } from './seller-dashboard/seller-dashboard.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { SalesReportComponent } from './sales-report/sales-report.component';
import { AddProductComponent } from './add-product/add-product.component';


@NgModule({
  declarations: [
    SellerDashboardComponent,
    EditProductComponent,
    AddProductComponent,
    SalesReportComponent
  ],
  imports: [
    CommonModule,
    SellerRoutingModule,
    FormsModule
  ]
})
export class SellerModule { }
