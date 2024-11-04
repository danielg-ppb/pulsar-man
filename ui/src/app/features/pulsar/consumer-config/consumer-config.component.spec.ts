import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsumerConfigComponent } from './consumer-config.component';

describe('ConsumerConfigComponent', () => {
  let component: ConsumerConfigComponent;
  let fixture: ComponentFixture<ConsumerConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsumerConfigComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsumerConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
