import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewWorksComponent } from './new-works.component';

describe('NewWorksComponent', () => {
  let component: NewWorksComponent;
  let fixture: ComponentFixture<NewWorksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewWorksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewWorksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
