import {Component, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import {ElementRef} from "react";
import {DatePipe, JsonPipe, NgForOf} from "@angular/common";
import {WebsocketService} from "../../../core/services/websocket-service";

@Component({
    selector: 'app-list-pulsar-messages',
    standalone: true,
    imports: [
        DatePipe,
        NgForOf,
        JsonPipe
    ],
    templateUrl: './list-pulsar-messages.component.html',
    styleUrl: './list-pulsar-messages.component.scss'
})
export class ListPulsarMessagesComponent {
    messages: any[] = [];
    private messageSubscription!: Subscription;

    @ViewChild('messagesContainer') private messagesContainer!: ElementRef<any>;

    constructor(private webSocketService: WebsocketService) {
    }

    ngOnInit(): void {
        // Subscribe to incoming messages from the WebSocket
        this.messageSubscription = this.webSocketService.getMessages().subscribe((message) => {
            this.messages.push(message);
            this.scrollToBottom();
        });
    }

    scrollToBottom(): void {
        try {
            // @ts-ignore
            this.messagesContainer.nativeElement.scrollTop = this.messagesContainer.nativeElement.scrollHeight;
        } catch (err) {
            console.error('Scroll error:', err);
        }
    }

    // Send a test message to WebSocket server
    sendTestMessage(): void {
        const message = {topic: 'my-topic', content: 'Test message from Angular'};
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
