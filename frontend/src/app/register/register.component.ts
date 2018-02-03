import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { AuthService } from '../login/auth.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  korisnik: KorisnikInterface;

  constructor(private authService: AuthService,
              private router: Router,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.korisnik = {
      ime: '',
      prezime: '',
      korisnicko_ime: '',
      lozinka: '',
      email: '',
      uloga: 'AUTOR'
    };
  }

  ngOnInit() {
  }
  
  jsToXmlFile(obj) {
    var builder = require('xmlbuilder');
    var root = builder.create('korisnik', {
      xmldec: false,
      stringify: {
        eleName: function(val) {
          return 'ko:' + val;
        }
      }
    });
    var ele = root.ele(this.korisnik);
    var atr = root.att('xmlns:ko', 'http://www.ftn.uns.ac.rs/korisnici');
    return root.end({ pretty: true });
  }

  save(){
    console.log(this.korisnik);
    var xml = this.jsToXmlFile(this.korisnik);
  
    this.authService.register(xml)
        .then(() => this.router.navigate(['prijava']))
        .catch(() => this.toastr.error('Registracija nije uspela'));
  }
}
