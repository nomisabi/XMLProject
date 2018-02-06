import { Component, OnInit } from '@angular/core';

import { AuthService } from '../login/auth.service';

@Component({
  selector: 'app-home-editor',
  templateUrl: './home-editor.component.html',
  styleUrls: ['./home-editor.component.css']
})
export class HomeEditorComponent implements OnInit {

  username: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
  }

  logout(){
    this.authService.logout();
  }

}
