import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { WorkService } from '../../works/work.service';
import * as FileSaver from 'file-saver'; 

@Component({
  selector: 'app-work-detail-editor',
  templateUrl: './work-detail-editor.component.html',
  styleUrls: ['./work-detail-editor.component.css']
})
export class WorkDetailEditorComponent implements OnInit {

  work: WorkInterface;
  rate: boolean;
  review: boolean;
  

  constructor(
              private workService: WorkService,
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
    this.review = false;
    this.rate = false;
    this.workService.getWork(this.route.snapshot.params['id'])
        .then(work => 
          {
            this.work = work;
            for(let revision of this.work.revisions){
              var prihvacen: number = 0;
              var ceka: number = 0;
              var odbijen: number = 0;
              if(revision.status === 'U_OBRADI'){
                for(let review of revision.reviews){
                  if(review.status === 'PRIHVACEN'){
                    prihvacen ++;
                  }else if(review.status === 'CEKA_SE'){
                    ceka ++;
                  }else if(review.status === 'ODBIJEN'){
                    odbijen ++;
                  }
                }

                if (prihvacen === 2){
                  console.log('oceni');
                  this.rate = true;
                }
                if (prihvacen < 2 && ceka === 0 && odbijen > 0){
                  console.log('dodaj recenzenta');
                  this.review = true;
                }
                if (prihvacen < 2 && ceka === 1 && odbijen > 0){
                  console.log('dodaj recenzenta');
                  this.review = true;
                }
              }

            }
          });
  }

  publish(id: string){
    this.workService.publishRevision(this.work.id, id)
      .then(() => this.getWork());
  }

  reject(id: string){
    this.workService.rejectRevision(this.work.id, id)
        .then(() => this.getWork());
  }

  revise(id: string){
    this.workService.reviseRevision(this.work.id, id)
        .then(() => this.getWork());
  }

  gotoAddReview(id: string, idRevizije: string){
    this.router.navigate([`/urednik/naucniRadovi/${id}/revizije/${idRevizije}/recenzent`]);
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

  gotoGetPDFPismo(id){
    this.workService.getPdfPismo(this.route.snapshot.params['id'], id).then(
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

  gotoGetXHTMLPismo(id){
    this.router.navigate(['urednik/naucniRadovi/'+this.route.snapshot.params['id']+'/revizija/'+id+'/pismo']);

  }

}
