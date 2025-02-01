import {Component, OnDestroy, OnInit} from '@angular/core';
import {WebsocketService} from "../../../core/services/websocket-service";
import {SseService} from "../../../core/services/sse-service";
import {Subscription} from "rxjs";
import {NgForOf, NgIf} from "@angular/common";

@Component({
    selector: 'app-pulsar-messages',
    imports: [
        NgIf,
        NgForOf
    ],
    templateUrl: './pulsar-messages.component.html',
    styleUrl: './pulsar-messages.component.scss'
})
export class PulsarMessagesComponent implements OnInit, OnDestroy {
    messages: string[] = [];
    private sseSubscription!: Subscription;

    constructor(private ssePulsarService: SseService) {
    }

    ngOnInit(): void {
        this.sseSubscription = this.ssePulsarService
            .connect('http://localhost:8080/stream-sse') // Update to match your backend URL
            .subscribe({
                next: (message) => this.messages.push(message),
                error: (err) => console.error('SSE error:', err),
            });
    }

    ngOnDestroy(): void {
        if (this.sseSubscription) {
            this.sseSubscription.unsubscribe();
        }
    }


}
