import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowXhtmlreviewerComponent } from './show-xhtmlreviewer.component';

describe('ShowXhtmlreviewerComponent', () => {
  let component: ShowXhtmlreviewerComponent;
  let fixture: ComponentFixture<ShowXhtmlreviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowXhtmlreviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowXhtmlreviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
