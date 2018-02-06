import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { UserService } from '../work-detail/user.service';
import { WorkService } from '../works/work.service';

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {

  work: WorkInterface;
  users: any;

  constructor(private userService: UserService,
              private workService: WorkService,
              private router: Router,
              private location: Location) {  }

  ngOnInit() {
    this.work = {
      id: 'ID955565',
      title: 'Blaa',
      status: '',
      review1: '',
      review2: ''
    }
    this.userService.getReviews()
    .then(reviews => {
      console.log(reviews);
      this.users = reviews.korisnik;
      console.log(this.users);
     
    });
  }

  save(){
    console.log(this.work.review1);
    this.workService.addReview(this.work)
        .then(res => this.router.navigate(['/urednik/naucniRadovi/uObradi']));
  }

  cancel(){
    this.location.back();
  }

}
