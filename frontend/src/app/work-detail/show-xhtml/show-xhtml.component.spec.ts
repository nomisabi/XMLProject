import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowXhtmlComponent } from './show-xhtml.component';

describe('ShowXhtmlComponent', () => {
  let component: ShowXhtmlComponent;
  let fixture: ComponentFixture<ShowXhtmlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowXhtmlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowXhtmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
