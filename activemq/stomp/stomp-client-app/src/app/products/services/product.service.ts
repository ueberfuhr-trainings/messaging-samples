import {Injectable} from '@angular/core';
import {StompService} from "../../stomp/stomp.service";
import {Observable, Subject} from "rxjs";
import {Product} from "../../product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly topic = '/topic/products-topic';

  constructor(private readonly stomp: StompService) { }

  public createSubscription(): Observable<Product> {
    const subject = new Subject<Product>();
    this.stomp.withClient(client => {
      const stompSubscription = client.subscribe(this.topic, message => {
        message.ack();
        try {
          const product: Product = JSON.parse(message.body);
          subject.next(product);
        } catch(e) {
          console.error(`Message was: `);
          console.error(message);
          console.error(`Tried to parse message body: ${message.body}`);
          console.error(e);
        }
        let originalComplete = subject.complete;
        subject.complete = () => {
          try {
            stompSubscription.unsubscribe();
          } finally {
            originalComplete();
          }
        }
      }, {ack: 'client'});
    });
    return subject.asObservable();
  }

  public send(product: Product): void {
    this.stomp.withClient(client => {
      client.send(this.topic, {}, JSON.stringify(product));
    });
  }

}
