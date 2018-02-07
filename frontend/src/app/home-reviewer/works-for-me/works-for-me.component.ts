import { Component, OnInit } from '@angular/core';

import { WorkService } from '../../works/work.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-works-for-me',
  templateUrl: './works-for-me.component.html',
  styleUrls: ['./works-for-me.component.css']
})
export class WorksForMeComponent implements OnInit {
  works: WorkInterface[];

  constructor(private workService: WorkService,
              private router: Router) { }

  ngOnInit() {
    this.getWorks();
  }

  getWorks(){
    this.workService.getWorksForReviewer()
      .then(works => this.works = works);
  }

  gotoWorkDetail(id: string){
    this.router.navigate([`/recenzent/naucniRadovi/${id}`]);

  }
}
