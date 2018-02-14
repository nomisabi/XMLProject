import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { WorkService } from '../../works/work.service';


@Component({
  selector: 'app-search-author',
  templateUrl: './search-author.component.html',
  styleUrls: ['./search-author.component.css']
})
export class SearchAuthorComponent implements OnInit {
  flag: boolean;
  param: string;
  works: WorkInterface[];

  constructor(private workService: WorkService,
              private router: Router) { }

  ngOnInit() {
    this.flag = true;
  }

  onChange(deviceValue) {
    console.log(deviceValue);
    if (deviceValue === "Napredna pretraga"){
      this.flag = false;
      this.works = [];
    }else{
      this.flag = true;
      this.works = [];
    }
  }

  find(){
    console.log(this.param);

    this.workService.searchAuthor(this.param)
        .then(works => this.works = works);

  }

  gotoWorkDetail(id: string){
    this.router.navigate([`/autor/naucniRadovi/${id}`]);
  }

}
