import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { WorkService } from '../../works/work.service';
import { UploadFileService } from '../../home-author/add-work/upload-file.service';
import * as FileSaver from 'file-saver'; 

@Component({
  selector: 'app-work-detail-reviewer',
  templateUrl: './work-detail-reviewer.component.html',
  styleUrls: ['./work-detail-reviewer.component.css']
})
export class WorkDetailReviewerComponent implements OnInit {
  selectedFiles: FileList
  currentFileUpload: File
  progress: { percentage: number } = { percentage: 0 }

  work: WorkInterface;
  flag: boolean;

  constructor(private workService:WorkService,
              private uploadService: UploadFileService,
              private route: ActivatedRoute,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef,
              private router: Router) { 
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    this.flag = false;
    this.getWork();
  }

  getWork(){
    this.workService.getWorkForReview(this.route.snapshot.params['id'],this.route.snapshot.params['idRevizije'] )
        .then(work => {
          console.log(work);
          this.work = work;
        })
  }

  yes(id: string){
    this.workService.acceptReview(this.work.id, id)
        .then(() => this.getWork());

  }

  no(id: string){
    this.workService.disardReview(this.work.id,id)
        .then(() => this.getWork());

  }

  add(){
    this.flag = true;

  }

  gotoAddReview(id: string){
    this.router.navigate([`/recenzent/naucniRadovi/${this.work.id}/revizije/${id}/recenzija`]);
  }

  gotoGetPDF(){
    this.workService.getPdfNoAuthor(this.route.snapshot.params['id']).then(
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
    this.router.navigate(['recenzent/naucniRadovi/'+this.route.snapshot.params['id']+'/xhtml']);

  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload(id: string) {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0)
    this.uploadService.pushFileReview(this.currentFileUpload,this.work.id, id)
    .subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        this.getWork();
        if (event.status === 200){
         console.log(event.body);
          this.toastr.success('Recenzije uspesno poslata.');
         // this.getWork();
        }
      }
    },
    error => {
      this.toastr.error('Greska prilikom slanja recenzije.');
      this.getWork()});
 
    this.selectedFiles = undefined
  }


}
