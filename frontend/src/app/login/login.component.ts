import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: UserInterface;

  constructor(private authService: AuthService,
              private router: Router,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.user = {
      username: '',
      password: ''
    }
  }

  ngOnInit() {
  }

  login(){
    this.authService.login(this.user.username, this.user.password)
      .then( res => {  
        this.toastr.success('Prijava uspesna!');
        if(this.authService.isAuthor()){
          this.router.navigate(['/autor']);
        } else if (this.authService.isEditor()){
          this.router.navigate(['/urednik']);
        } else if (this.authService.isReviewer()) {
          this.router.navigate(['recenzent']);
        }      
      })
      .catch(error => {
        this.toastr.error('Korisnicko ime ili lozinka nisu ispravni.');
      });
  }

  gotoRegister(){
    this.router.navigate(['registracija']);

  }

}
