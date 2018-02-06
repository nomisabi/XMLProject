import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { UserService } from '../work-detail/user.service';
import { WorkService } from '../works/work.service';


@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {

  revision: RevisionInterface;
  users: any;

  constructor(private userService: UserService,
              private workService: WorkService,
              private router: Router,
              private route: ActivatedRoute,
              private location: Location) {  }

  ngOnInit() {
    this.revision = {
      id: 'ID955565',
      title: 'Blaa',
      status: '',
      review1: '',
      review2: '',
      hasLetter: false
    }
    this.userService.getReviews()
    .then(reviews => {
      console.log(reviews);
      this.users = reviews.korisnik;
      console.log(this.users);
     
    });
  }

  save(){
    console.log(this.revision.review1);
    var id = this.route.snapshot.params['id'];
    var idRevision = this.route.snapshot.params['idRevizije'];
    this.workService.addReview(id,idRevision, this.revision)
        .then(res => this.router.navigate(['/urednik/naucniRadovi/uObradi']));
  }

  cancel(){
    this.location.back();
  }

}
