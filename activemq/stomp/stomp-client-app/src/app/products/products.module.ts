import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProductListComponent} from './components/product-list/product-list.component';
import {ProductProducerComponent} from './components/product-producer/product-producer.component';
import {FormsModule} from '@angular/forms';
import {NiceDatetimeFormatModule} from '../nice-datetime-format/nice-datetime-format.module';

@NgModule({
  declarations: [ProductListComponent, ProductProducerComponent],
  exports: [
    ProductListComponent,
    ProductProducerComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    NiceDatetimeFormatModule,
  ]
})
export class ProductsModule {
}
