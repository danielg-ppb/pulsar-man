import {Routes} from '@angular/router'
import {ErrorComponent} from './features/error/error.component'
import {HomeComponent} from './features/home/home.component'
import {MaintenanceComponent} from './features/maintenance/maintenance.component'
import {MaintenanceGuard} from './core/guards/maintenance/maintenance.guard'
import {
    ConfigureClientProviderComponent
} from "./features/pulsar/configure-client-provider/configure-client-provider.component";
import {ListPulsarMessagesComponent} from "./features/pulsar/list-pulsar-messages/list-pulsar-messages.component";
import {ConsumerConfigComponent} from "./features/pulsar/consumer-config/consumer-config.component";
import {PulsarMessagesComponent} from "./features/pulsar/pulsar-messages/pulsar-messages.component";

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
    },
    {
        path: 'maintenance',
        component: MaintenanceComponent,
    },
    {
        path: 'error',
        component: ErrorComponent,
        canActivate: [MaintenanceGuard],
    },
    {
        path: 'configure-client-provider',
        component: ConfigureClientProviderComponent
    },
    {
        path: 'list-messages',
        component: ListPulsarMessagesComponent
    },
    {
        path: 'pulsar-messages',
        component: PulsarMessagesComponent
    },
    {
        path: 'configure-consumer',
        component: ConsumerConfigComponent
    },
    //must always be last
    {
        path: '**',
        redirectTo: 'error',
    }

]
