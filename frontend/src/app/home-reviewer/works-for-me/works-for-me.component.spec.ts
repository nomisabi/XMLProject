import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorksForMeComponent } from './works-for-me.component';

describe('WorksForMeComponent', () => {
  let component: WorksForMeComponent;
  let fixture: ComponentFixture<WorksForMeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorksForMeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorksForMeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
