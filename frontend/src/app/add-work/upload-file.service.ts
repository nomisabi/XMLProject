import { Injectable } from '@angular/core';
import {HttpClient, HttpRequest, HttpEvent} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class UploadFileService {

  constructor(private http: HttpClient) {}
 
  pushFileToStorage(file: File): Observable<HttpEvent<{}>> {
    let formdata: FormData = new FormData();
 
    formdata.append('file', file);
 
    const req = new HttpRequest('POST', '/api/naucni_radovi', formdata, {
      reportProgress: true,
      responseType: 'text'
    });
 
    return this.http.request(req);
  }

}
