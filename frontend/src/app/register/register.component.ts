import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  korisnik: KorisnikInterface;
  fs = require('fs');
  path = require('path');
  xml2js = require('xml2js');

  constructor() { 
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
    var builder = new this.xml2js.Builder({rootName: "korisnik"});
    var xml = builder.buildObject(obj);
    console.log(xml);
  }

  save(){
    console.log(this.korisnik);
    this.jsToXmlFile(this.korisnik);
  }
}
