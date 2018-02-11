import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { WorkService } from '../../works/work.service';
import { UploadFileService } from '../add-work/upload-file.service';


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

  constructor(private workService:WorkService,
              private uploadService: UploadFileService,
              private toastr: ToastsManager, 
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

}
