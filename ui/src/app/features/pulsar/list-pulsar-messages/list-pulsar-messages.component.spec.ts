import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPulsarMessagesComponent } from './list-pulsar-messages.component';

describe('ListPulsarMessagesComponent', () => {
  let component: ListPulsarMessagesComponent;
  let fixture: ComponentFixture<ListPulsarMessagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListPulsarMessagesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListPulsarMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
