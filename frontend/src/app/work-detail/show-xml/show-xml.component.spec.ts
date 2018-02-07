import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowXmlComponent } from './show-xml.component';

describe('ShowXmlComponent', () => {
  let component: ShowXmlComponent;
  let fixture: ComponentFixture<ShowXmlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowXmlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowXmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
