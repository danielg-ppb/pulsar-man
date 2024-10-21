import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {PulsarMessagesComponent} from "./components/pulsar-messages/pulsar-messages.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PulsarMessagesComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'pulsar-websocket-app';
}
