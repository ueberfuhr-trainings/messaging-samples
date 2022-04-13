import {Component, OnDestroy, OnInit} from '@angular/core';
import {Product} from "../../../product";
import {ProductService} from "../../services/product.service";
import {Subscription} from "rxjs";

interface ProductUpdate {
  product: Product,
  timestamp: Date
}

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit, OnDestroy {

  products: ProductUpdate[] = [];
  subscription?: Subscription;

  constructor(private readonly productService: ProductService) {
  }

  ngOnInit(): void {
    this.subscription = this.productService.createSubscription().subscribe(p => {
      // we need to copy the array
      this.products.unshift({
        product: p,
        timestamp: new Date()
      });
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
