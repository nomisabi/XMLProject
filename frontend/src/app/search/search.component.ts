import { Component, OnInit } from '@angular/core';
import { WorkService } from '../works/work.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
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

    this.workService.search(this.param)
        .then(works => this.works = works);

  }

  gotoWorkDetail(id: string){
    if(this.router.url.startsWith('/urednik')){
      this.router.navigate([`/urednik/naucniRadovi/${id}`]);
    }else if(this.router.url.startsWith('/autor')){
      this.router.navigate([`/autor/naucniRadovi/objavljeni/${id}`]);
    }else{
      this.router.navigate([`/naucniRadovi/${id}`]);
    }
  }

}
