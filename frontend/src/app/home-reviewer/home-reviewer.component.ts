import { Component, OnInit } from '@angular/core';

import { AuthService } from '../login/auth.service';

@Component({
  selector: 'app-home-reviewer',
  templateUrl: './home-reviewer.component.html',
  styleUrls: ['./home-reviewer.component.css']
})
export class HomeReviewerComponent implements OnInit {

  username: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
  }

  logout(){
    this.authService.logout();
  }

}
