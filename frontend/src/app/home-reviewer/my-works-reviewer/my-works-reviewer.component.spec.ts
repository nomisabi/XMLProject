import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyWorksReviewerComponent } from './my-works-reviewer.component';

describe('MyWorksReviewerComponent', () => {
  let component: MyWorksReviewerComponent;
  let fixture: ComponentFixture<MyWorksReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyWorksReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyWorksReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
