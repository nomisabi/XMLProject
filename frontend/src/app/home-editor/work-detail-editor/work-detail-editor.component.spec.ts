import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkDetailEditorComponent } from './work-detail-editor.component';

describe('WorkDetailEditorComponent', () => {
  let component: WorkDetailEditorComponent;
  let fixture: ComponentFixture<WorkDetailEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkDetailEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkDetailEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
