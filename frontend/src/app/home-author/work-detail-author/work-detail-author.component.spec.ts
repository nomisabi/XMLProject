import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkDetailAuthorComponent } from './work-detail-author.component';

describe('WorkDetailAuthorComponent', () => {
  let component: WorkDetailAuthorComponent;
  let fixture: ComponentFixture<WorkDetailAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkDetailAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkDetailAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
