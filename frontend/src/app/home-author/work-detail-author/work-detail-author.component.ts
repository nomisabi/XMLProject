import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { WorkService } from '../../works/work.service';
import { UploadFileService } from '../add-work/upload-file.service';
import * as FileSaver from 'file-saver'; 

@Component({
  selector: 'app-work-detail-author',
  templateUrl: './work-detail-author.component.html',
  styleUrls: ['./work-detail-author.component.css']
})
export class WorkDetailAuthorComponent implements OnInit {
  work: WorkInterface;
  selectedFiles: FileList
  currentFileUpload: File
  progress: { percentage: number } = { percentage: 0 }
  addRdf=false;
  rdfText='';

  constructor(private workService:WorkService,
              private uploadService: UploadFileService,
              private toastr: ToastsManager, 
              private router: Router,
              private vcr: ViewContainerRef,
              private route: ActivatedRoute) { 
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    this.getWork();

  }

  getWork(){
    this.workService.getWork(this.route.snapshot.params['id'])
        .then(work => {
          console.log(work);
          this.work = work;
          for(let i in this.work.revisions){
            if(this.work.revisions[i].status === 'POTREBNA_IZMENA'){
              this.work.revisions[i].flag = true;
              if (this.work.revisions.length-1 > +i){
                this.work.revisions[i].flag = false;
              }
              var j: number = +i;
              j++;

              while(j < this.work.revisions.length){
                if (this.work.revisions[j].status === 'OBRISAN' && this.work.revisions.length === j+1){
                  this.work.revisions[i].flag = true;
                }
                if (this.work.revisions[j].status !== 'OBRISAN'){
                  this.work.revisions[i].flag = false;
                  break;
                }
                j++;
              }
              
              
            }
          }
        })
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload(id: string) {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0)
    this.uploadService.pushFile(this.currentFileUpload,this.work.id, id)
    .subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        if (event.status === 200){
         console.log(event.body);
          this.toastr.success('Pismo uspesno poslato.');
          this.getWork();

        }else if (event.status === 417){
          this.toastr.error('Greska prilikom slanja pisma.')
        }
      }
    });
   // .catch(err =>  this.toastr.error('Greska prilikom slanja naucnog rada.'));
 
    this.selectedFiles = undefined
  }

  uploadRevision() {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0)
    this.uploadService.pushFileRevision(this.currentFileUpload,this.work.id)
    .subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        if (event.status === 200){
         console.log(event.body);
          this.toastr.success('Revizija uspesno poslata.');
          this.getWork();

        }else if (event.status === 417){
          this.toastr.error('Greska prilikom slanja revizije.')
        }
      }
    });
   // .catch(err =>  this.toastr.error('Greska prilikom slanja naucnog rada.'));
 
    this.selectedFiles = undefined
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
    this.router.navigate(['autor/naucniRadovi/'+this.route.snapshot.params['id']+'/xhtml']);

  }

  gotoGetXML(){
    this.router.navigate(['autor/naucniRadovi/'+this.route.snapshot.params['id']+'/xml']);
  }

  goToRevizija(id){
    console.log('gorev');
    this.router.navigate(['autor/naucniRadovi/'+this.route.snapshot.params['id']+'/revizija/'+id]);
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
    this.router.navigate(['autor/naucniRadovi/'+this.route.snapshot.params['id']+'/revizija/'+id+'/pismo']);

  }

  showAddRdf(){
    if (this.addRdf)
      this.addRdf=false;
    else
      this.addRdf=true;
  }

  addNewRdf(){
    console.log(this.rdfText);
    this.workService.addRdf(this.route.snapshot.params['id'], this.rdfText);
    this.rdfText='';
    this.showAddRdf();
  }

  gotoGetRDF(){
    this.workService.getRdfRDF(this.route.snapshot.params['id']).then(
      rdf=> {var blob = new Blob([rdf], {type: "text/plain;charset=utf-8"});
      FileSaver.saveAs(blob, "metadata.rdf");
    }
    )
  }

  gotoGetJSON(){
    this.workService.getRdfJSON(this.route.snapshot.params['id']).then(
      rdf=> {var blob = new Blob([rdf], {type: "application/json;charset=utf-8"});
      FileSaver.saveAs(blob, "metadata.json");
    }
    )
  }
}
