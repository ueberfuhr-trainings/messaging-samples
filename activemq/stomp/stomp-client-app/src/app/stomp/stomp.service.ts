import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {CompatClient, Stomp} from "@stomp/stompjs";

@Injectable({
  providedIn: 'root'
})
export class StompService {

  private readonly client = Stomp.client(environment.stomp.endpoint);
  private connected = false;

  public withClient(action: (value: CompatClient) => void): void {
    if(this.connected){
      action(this.client);
    } else {
      this.client.connect(environment.stomp.username, environment.stomp.password, () => {
        this.connected = true;
        action(this.client);
      });
    }
  }

}
