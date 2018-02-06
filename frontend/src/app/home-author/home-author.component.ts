import { Component, OnInit } from '@angular/core';
import { AuthService } from '../login/auth.service';

@Component({
  selector: 'app-home-author',
  templateUrl: './home-author.component.html',
  styleUrls: ['./home-author.component.css']
})
export class HomeAuthorComponent implements OnInit {
  username: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
  }

  logout(){
    this.authService.logout();
  }

}
