import {ApplicationConfig} from '@angular/core'
import {provideRouter} from '@angular/router'

import {routes} from './app.routes'
import {provideClientHydration} from '@angular/platform-browser'
import {provideHttpClient, withFetch} from "@angular/common/http";
import {provideAnimations} from '@angular/platform-browser/animations';
import Aura from '@primeng/themes/aura';
import {providePrimeNG} from "primeng/config";


export const appConfig: ApplicationConfig = {
    providers: [provideRouter(routes), provideClientHydration(), provideHttpClient(), provideAnimations(),
        provideHttpClient(withFetch()),
        providePrimeNG({theme: {preset: Aura, options: {darkModeSelector: '.app-dark'}}})
    ],
}
