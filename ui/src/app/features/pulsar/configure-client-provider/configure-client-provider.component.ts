import {Component} from '@angular/core';
import {FloatLabelModule} from "primeng/floatlabel";
import {InputTextModule} from "primeng/inputtext";
import {PaginatorModule} from "primeng/paginator";
import {CommonModule} from "@angular/common";
import {Button} from "primeng/button";
import {PulsarProviderService} from "../../../core/services/pulsar-provider.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-configure-client-provider',
    standalone: true,
    imports: [
        FloatLabelModule,
        InputTextModule,
        PaginatorModule,
        CommonModule,
        Button
    ],
    templateUrl: './configure-client-provider.component.html',
    styleUrl: './configure-client-provider.component.scss'
})
export class ConfigureClientProviderComponent {
    inputText: string = 'pulsar://localhost:6650';
    token: string = "";

    constructor(private pulsarProviderService: PulsarProviderService, private router: Router) {
    }

    submitPulsarClientProvider() {
        const clientProvider = {
            serviceUrl: this.inputText,
            token: this.token
        }
        this.pulsarProviderService.postPulsarProviderServiceUrl(clientProvider).subscribe({
            next: () => {
                this.navigateToConsumerConfig();
            },
            error: error => {
                console.error('Error saving pulsar provider service url', error);
            }
        });
    }

    navigateToConsumerConfig() {
        // navigate to list-messages component
        this.router.navigate(['/configure-consumer']);
    }
}
