import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeReviewerComponent } from './home-reviewer.component';

describe('HomeReviewerComponent', () => {
  let component: HomeReviewerComponent;
  let fixture: ComponentFixture<HomeReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
