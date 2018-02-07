import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkDetailReviewerComponent } from './work-detail-reviewer.component';

describe('WorkDetailReviewerComponent', () => {
  let component: WorkDetailReviewerComponent;
  let fixture: ComponentFixture<WorkDetailReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkDetailReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkDetailReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
