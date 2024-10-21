import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import {WebSocketService} from "../../../service/WebSocketService";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-pulsar-messages',
  templateUrl: './pulsar-messages.component.html',
  standalone: true,
  imports: [
    NgForOf
  ],
  styleUrls: ['./pulsar-messages.component.css']
})
export class PulsarMessagesComponent implements OnInit, OnDestroy {
  messages: any[] = [];
  private messageSubscription!: Subscription;

  constructor(private webSocketService: WebSocketService) { }

  ngOnInit(): void {
    // Subscribe to incoming messages from the WebSocket
    this.messageSubscription = this.webSocketService.getMessages().subscribe((message) => {
      console.log(message);
      this.messages.push(message); // Add new messages to the array
    });
  }

  // Send a test message to WebSocket server
  sendTestMessage(): void {
    const message = { topic: 'my-topic', content: 'Test message from Angular' };
    this.webSocketService.sendMessage(message);
  }

  ngOnDestroy(): void {
    // Unsubscribe from the WebSocket messages to prevent memory leaks
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
    // Close WebSocket connection when the component is destroyed
    this.webSocketService.close();
  }
}
