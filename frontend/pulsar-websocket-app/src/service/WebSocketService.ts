import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private socket$: WebSocketSubject<any>;

  constructor() {
    this.socket$ = webSocket({
      url: 'ws://localhost:8080/pulsar/messages',
      deserializer: (msg) => {
        console.log('Received message:', msg.data);
        try {
          return JSON.parse(msg.data);
        } catch (e) {
          console.error('Error parsing JSON:', e);
          throw e;
        }
      }
    });
  }

  public getMessages() {
    return this.socket$.asObservable();
  }

  public sendMessage(msg: any) {
    this.socket$.next(msg);
  }

  public close() {
    this.socket$.complete();
  }
}
