import {ApplicationConfig} from '@angular/core'
import {provideRouter} from '@angular/router'

import {routes} from './app.routes'
import {provideClientHydration} from '@angular/platform-browser'
import { provideHttpClient } from "@angular/common/http";
import {provideAnimations} from '@angular/platform-browser/animations';
import Aura from '@primeng/themes/aura';


export const appConfig: ApplicationConfig = {
    providers: [provideRouter(routes), provideClientHydration(), provideHttpClient(), provideAnimations(),
        /*providePrimeNG({ theme: { preset: Aura, options: { darkModeSelector: '.app-dark' } } })*/
    ],
}
