import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WorkService } from '../../works/work.service';

@Component({
  selector: 'app-work-detail-reviewer',
  templateUrl: './work-detail-reviewer.component.html',
  styleUrls: ['./work-detail-reviewer.component.css']
})
export class WorkDetailReviewerComponent implements OnInit {

  work: WorkInterface;

  constructor(private workService:WorkService,
              private route: ActivatedRoute) { 
    
  }

  ngOnInit() {
    this.getWork();

  }

  getWork(){
    this.workService.getWorkForReview(this.route.snapshot.params['id'],this.route.snapshot.params['idRevizije'] )
        .then(work => {
          console.log(work);
          this.work = work;
        })
  }

  yes(id: string){
    this.workService.acceptReview(this.work.id, id)
        .then(() => this.getWork());

  }

  no(id: string){
    this.workService.disardReview(this.work.id,id)
        .then(() => this.getWork());

  }

  

  

}
