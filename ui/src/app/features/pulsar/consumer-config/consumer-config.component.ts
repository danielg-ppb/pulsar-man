import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {ButtonDirective} from "primeng/button";
import {FloatLabelModule} from "primeng/floatlabel";
import {DropdownModule} from "primeng/dropdown";
import {FileSelectEvent, FileUploadEvent, FileUploadModule} from "primeng/fileupload";
import {MessageService} from 'primeng/api';
import {PulsarConsumerService} from "../../../core/services/pulsar-consumer.service";
import {NgIf} from "@angular/common";
import {ConsumerConfig} from "../../../shared/model/consumer-config";
import {ToastModule} from "primeng/toast";
import {Router} from "@angular/router";


interface DropdownOption {
    id: string;
    value: string;
}

@Component({
    selector: 'app-consumer-config',
    imports: [
        ReactiveFormsModule,
        InputTextModule,
        ButtonDirective,
        FloatLabelModule,
        DropdownModule,
        FileUploadModule,
        ToastModule,
        NgIf
    ],
    templateUrl: './consumer-config.component.html',
    styleUrl: './consumer-config.component.scss',
    providers: [MessageService]
})
export class ConsumerConfigComponent implements OnInit {
    formGroup!: FormGroup;
    currentFile?: File;
    schemaTypes: DropdownOption[] = [
        {id: 'string', value: 'String'},
        {id: 'avro', value: 'Avro'},
        {id: 'json', value: 'JSON'},
        {id: 'protobuf', value: 'Protobuf'},
        {id: 'binary', value: 'Binary'}
    ];
    subscriptionTypes: DropdownOption[] = [
        {id: 'Exclusive', value: 'Exclusive'},
        {id: 'Failover', value: 'Failover'},
        {id: 'Shared', value: 'Shared'},
        {id: 'Key_Shared', value: 'Key_Shared'}
    ];
    initialPositions: DropdownOption[] = [
        {id: 'earliest', value: 'Earliest'},
        {id: 'latest', value: 'Latest'}
    ];

    constructor(private fb: FormBuilder,
                private messageService: MessageService,
                private consumerService: PulsarConsumerService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.formGroup = this.fb.group({
            topicName: ['persistent://gst/trading/topic-mapped-entities'],
            subscriptionName: ['entity-manager-subscription-abc123'],
            subscriptionType: ['Key_Shared'],
            schemaType: ['protobuf'],
            initialPosition: ['earliest'],
            outerClassName: ['MegaProto'],
            mainInnerClassName: ['EntityEnvelopeProto']
        });
    }


    onSubmit() {
        const consumerConfig: ConsumerConfig = {
            topicName: this.formGroup.get('topicName')?.value,
            subscriptionName: this.formGroup.get('subscriptionName')?.value,
            subscriptionType: this.formGroup.get('subscriptionType')?.value,
            schemaType: this.formGroup.get('schemaType')?.value,
            initialPosition: this.formGroup.get('initialPosition')?.value,
            outerClassName: this.formGroup.get('outerClassName')?.value,
            mainInnerClassName: this.formGroup.get('mainInnerClassName')?.value
        }

        this.consumerService.postPulsarDynamicConsumer(this.currentFile!, consumerConfig).subscribe(
            {
                next: () => {
                    this.messageService.add({severity: 'info', summary: 'Success', detail: 'Consumer Created'});

                },
                error: error => {
                    this.messageService.add({severity: 'error', summary: 'Error', detail: 'Consumer Creation Failed'});
                },
                complete: () => {
                    this.navigateToMessageList();
                }
            }
        );
    }

    onUpload(event: FileUploadEvent) {
        this.messageService.add({severity: 'info', summary: 'Success', detail: 'File Uploaded with Basic Mode'});
        console.log(event.files);
    }

    onFileChange($event: FileSelectEvent) {
        this.messageService.add({severity: 'info', summary: 'Success', detail: 'File Added'});
        this.currentFile = $event.files[0];
    }

    navigateToMessageList() {
        // navigate to list-messages component
        this.router.navigate(['/list-messages']);
    }
}
