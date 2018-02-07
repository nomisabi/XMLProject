import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressWorkComponent } from './progress-work.component';

describe('ProgressWorkComponent', () => {
  let component: ProgressWorkComponent;
  let fixture: ComponentFixture<ProgressWorkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProgressWorkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgressWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
