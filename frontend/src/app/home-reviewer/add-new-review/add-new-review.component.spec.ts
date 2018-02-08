import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewReviewComponent } from './add-new-review.component';

describe('AddNewReviewComponent', () => {
  let component: AddNewReviewComponent;
  let fixture: ComponentFixture<AddNewReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNewReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
