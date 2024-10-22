import {Component, OnInit, OnDestroy, LOCALE_ID, ViewChild, ElementRef} from '@angular/core';
import { Subscription } from 'rxjs';
import {WebSocketService} from "../../../service/WebSocketService";
import {DatePipe, NgForOf} from "@angular/common";

@Component({
  selector: 'app-pulsar-messages',
  templateUrl: './pulsar-messages.component.html',
  standalone: true,
  imports: [
    NgForOf,
    DatePipe
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt' } // Set the locale globally to French
  ],
  styleUrls: ['./pulsar-messages.component.css']
})
export class PulsarMessagesComponent implements OnInit, OnDestroy {
  messages: any[] = [];
  private messageSubscription!: Subscription;

  @ViewChild('messagesContainer') private messagesContainer!: ElementRef;

  constructor(private webSocketService: WebSocketService) { }

  ngOnInit(): void {
    // Subscribe to incoming messages from the WebSocket
    this.messageSubscription = this.webSocketService.getMessages().subscribe((message) => {
      console.log(message);
      this.messages.push(message); // Add new messages to the array
      this.scrollToBottom();
    });
  }

  scrollToBottom(): void {
    try {
      this.messagesContainer.nativeElement.scrollTop = this.messagesContainer.nativeElement.scrollHeight;
    } catch (err) {
      console.error('Scroll error:', err);
    }
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
