import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {ClientProvider} from "../../shared/model/client-provider";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class PulsarProviderService {
    constructor(private http: HttpClient) {
    }

    postPulsarProviderServiceUrl(clientProvider: ClientProvider): Observable<any> {
        return this.http.post<any>(`${environment.apiUrl}/pulsar-provider/service-url`, clientProvider);
    }
}
