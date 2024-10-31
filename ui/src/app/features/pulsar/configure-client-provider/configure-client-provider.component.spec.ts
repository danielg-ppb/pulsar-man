import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigureClientProviderComponent } from './configure-client-provider.component';

describe('ConfigureClientProviderComponent', () => {
  let component: ConfigureClientProviderComponent;
  let fixture: ComponentFixture<ConfigureClientProviderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfigureClientProviderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfigureClientProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
