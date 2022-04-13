import {Component} from '@angular/core';
import {Product} from "../../../product";
import {ProductService} from "../../services/product.service";

@Component({
  selector: 'app-product-producer',
  templateUrl: './product-producer.component.html',
})
export class ProductProducerComponent {

  product: Product = {
    shortcut: '',
    name: '',
    price: 3.99,
    stock: 20
  };

  constructor(private readonly productService: ProductService) {
  }

  onSubmit() {
    this.productService.send(this.product);
  }
}
