import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { WorkService } from './work.service';


@Component({
  selector: 'app-works',
  templateUrl: './works.component.html',
  styleUrls: ['./works.component.css']
})
export class WorksComponent implements OnInit {
  works: WorkInterface[];

  constructor(private workService: WorkService,
              private router: Router) { }

  ngOnInit() {
    this.getWorks();
  }

  getWorks(){
    this.workService.getWorks()
        .then(works => this.works = works);
  }

  gotoWorkDetail(id: string){
    this.router.navigate([`/naucniRadovi/${id}`]);

  }

}
