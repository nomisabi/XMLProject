import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { WorkService } from '../works/work.service';


@Component({
  selector: 'app-my-works',
  templateUrl: './my-works.component.html',
  styleUrls: ['./my-works.component.css']
})
export class MyWorksComponent implements OnInit {

  works: WorkInterface[];

  constructor(private workService: WorkService,
              private router: Router) { }

  ngOnInit() {
    this.getWorks();
  }

  getWorks(){
    this.workService.myWorks()
        .then(works => this.works = works);
  }

  gotoWorkDetail(id: string){
    this.router.navigate([`/autor/naucniRadovi/${id}`]);
  }

  gotoDelete(id: string, revisionID: string){
    this.workService.deleteWork(id, revisionID)
        .then(() => this.getWorks());

  }

}
