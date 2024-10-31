import { Component } from '@angular/core'
import { CommonModule } from '@angular/common'
import {ListPulsarMessagesComponent} from "../pulsar/list-pulsar-messages/list-pulsar-messages.component";
import {
    ConfigureClientProviderComponent
} from "../pulsar/configure-client-provider/configure-client-provider.component";

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommonModule, ListPulsarMessagesComponent, ConfigureClientProviderComponent],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
})
export class HomeComponent {}
