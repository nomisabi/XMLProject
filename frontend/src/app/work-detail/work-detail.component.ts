import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { WorkService } from '../works/work.service';
import * as FileSaver from 'file-saver'; 


@Component({
  selector: 'app-work-detail',
  templateUrl: './work-detail.component.html',
  styleUrls: ['./work-detail.component.css']
})
export class WorkDetailComponent implements OnInit {
  work: WorkInterface;
  

  constructor(private workService: WorkService,
              private router: Router,
              private route: ActivatedRoute,
              private location: Location) {  }

  ngOnInit() {
    this.work = {
      id: '',
      revisions: null
    }
    this.getWork(); 
  
  }

  getWork(){
    this.workService.getWork(this.route.snapshot.params['id'])
        .then(work => this.work = work);
  }


  gotoGetPDF(){
    this.workService.getPdf(this.route.snapshot.params['id']).then(
        response=>{
        let blob = new Blob([response], { 
          type: 'application/pdf' // must match the Accept type
        });

        var filename = 'mypdf.pdf';
        console.log(blob);
        console.log(response);
        FileSaver.saveAs(blob, filename);
        }
    )
  }

  gotoGetXHTML(){
    this.router.navigate(['naucniRadovi/'+this.route.snapshot.params['id']+'/xhtml']);

  }

  gotoGetXML(){
    this.router.navigate(['naucniRadovi/'+this.route.snapshot.params['id']+'/xml']);
  }

  goToRevizija(id){
    console.log('gorev');
    this.router.navigate(['naucniRadovi/'+this.route.snapshot.params['id']+'/revizija/'+id]);
  }
}
