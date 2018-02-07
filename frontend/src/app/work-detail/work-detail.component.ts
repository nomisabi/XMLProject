import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { UserService } from './user.service';
import { WorkService } from '../works/work.service';


@Component({
  selector: 'app-work-detail',
  templateUrl: './work-detail.component.html',
  styleUrls: ['./work-detail.component.css']
})
export class WorkDetailComponent implements OnInit {
  work: WorkInterface;
  users: any;

  constructor(private userService: UserService,
              private workService: WorkService,
              private router: Router,
              private location: Location) {  }

  ngOnInit() {
    this.work = {
      id: '',
      revisions: null
    }
    this.userService.getReviews()
    .then(reviews => {
      console.log(reviews);
      this.users = reviews.korisnik;
      console.log(this.users);
     
    });
  }


  gotoGetPDF(){

  }

  gotoGetXHTML(){
    
  }

}
