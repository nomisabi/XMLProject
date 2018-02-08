import { Component, OnInit } from '@angular/core';
import { WorkService } from '../../works/work.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-works-reviewer',
  templateUrl: './my-works-reviewer.component.html',
  styleUrls: ['./my-works-reviewer.component.css']
})
export class MyWorksReviewerComponent implements OnInit {
  works: WorkInterface[];

  constructor(private workService: WorkService,
              private router: Router) { }

  ngOnInit() {
    this.getWorks();
  }

  getWorks(){
    this.workService.getMyWorksForReviewer()
      .then(works => this.works = works);
  }

  gotoWorkDetail(id: string, idRevision: string){
    this.router.navigate([`/recenzent/naucniRadovi/${id}/revizije/${idRevision}`]);

  }

}
