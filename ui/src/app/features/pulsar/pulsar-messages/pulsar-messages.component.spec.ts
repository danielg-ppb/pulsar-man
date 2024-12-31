import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PulsarMessagesComponent } from './pulsar-messages.component';

describe('PulsarMessagesComponent', () => {
  let component: PulsarMessagesComponent;
  let fixture: ComponentFixture<PulsarMessagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PulsarMessagesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PulsarMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
