import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ClientProvider} from "../../shared/model/client-provider";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {ConsumerConfig} from "../../shared/model/consumer-config";

@Injectable({
    providedIn: 'root'
})
export class PulsarConsumerService {

    constructor(private http: HttpClient) {
    }

    postPulsarDynamicConsumer(file: File, consumerConfig: ConsumerConfig): Observable<any> {
        const formData = new FormData();
        formData.append('protoFile', file, file.name);
        formData.append('topicName', consumerConfig.topicName);
        formData.append('subscriptionName', consumerConfig.subscriptionName);
        formData.append('subscriptionType', consumerConfig.subscriptionType);
        formData.append('schemaType', consumerConfig.schemaType);
        formData.append('initialPosition', consumerConfig.initialPosition);
        formData.append('outerClassName', consumerConfig.outerClassName);
        formData.append('mainInnerClassName', consumerConfig.mainInnerClassName);


        return this.http.post(`${environment.apiUrl}/pulsar-consumer/dynamic-initialize`, formData);
    }
}
