import { Component } from '@angular/core'
import { CommonModule } from '@angular/common'
import {
    ConfigureClientProviderComponent
} from "../pulsar/configure-client-provider/configure-client-provider.component";

@Component({
    selector: 'app-home',
    imports: [CommonModule, ConfigureClientProviderComponent],
    templateUrl: './home.component.html',
    standalone: true,
    styleUrl: './home.component.scss'
})
export class HomeComponent {}
