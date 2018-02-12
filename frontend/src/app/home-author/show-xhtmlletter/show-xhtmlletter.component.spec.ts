import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowXhtmlletterComponent } from './show-xhtmlletter.component';

describe('ShowXhtmlletterComponent', () => {
  let component: ShowXhtmlletterComponent;
  let fixture: ComponentFixture<ShowXhtmlletterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowXhtmlletterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowXhtmlletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
