import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { UploadFileService } from './upload-file.service';


@Component({
  selector: 'app-add-work',
  templateUrl: './add-work.component.html',
  styleUrls: ['./add-work.component.css']
})
export class AddWorkComponent implements OnInit {
  selectedFiles: FileList
  currentFileUpload: File
  progress: { percentage: number } = { percentage: 0 }
 
  constructor(private uploadService: UploadFileService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef,
              private router: Router) {
    this.toastr.setRootViewContainerRef(vcr);
   }
 
  ngOnInit() {
  }
 
  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
 
  upload() {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0)
    this.uploadService.pushFileToStorage(this.currentFileUpload)
    .subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        if (event.status === 200){
         console.log(event.body);
          this.toastr.success('Naucni rad uspesno poslat.');
          this.router.navigate([`/autor/naucniRadovi/${event.body}`]);

        }else if (event.status === 417){
          this.toastr.error('Greska prilikom slanja naucnog rada.')
        }
      }
    });
   // .catch(err =>  this.toastr.error('Greska prilikom slanja naucnog rada.'));
 
    this.selectedFiles = undefined
  }

}
