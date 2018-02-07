import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { WorkService } from '../works/work.service';


@Component({
  selector: 'app-new-works',
  templateUrl: './new-works.component.html',
  styleUrls: ['./new-works.component.css']
})
export class NewWorksComponent implements OnInit {
  works: WorkInterface[];

  constructor(private workService: WorkService,
              private router: Router) { }

  ngOnInit() {
    this.getWorks();
  }

  getWorks(){
    this.workService.newWorks()
        .then(works => this.works = works);
  }

  gotoWorkDetail(id: string, idRevizije: string){
    this.router.navigate([`/urednik/naucniRadovi/${id}/revizije/${idRevizije}/recenzent`]);

  }

}
