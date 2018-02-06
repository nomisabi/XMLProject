import { Component, OnInit } from '@angular/core';
import { WorkService } from '../works/work.service';

@Component({
  selector: 'app-my-works',
  templateUrl: './my-works.component.html',
  styleUrls: ['./my-works.component.css']
})
export class MyWorksComponent implements OnInit {

  works: WorkInterface[];

  constructor(private workService: WorkService) { }

  ngOnInit() {
    this.getWorks();
  }

  getWorks(){
    this.workService.myWorks()
        .then(works => this.works = works);
  }

}
