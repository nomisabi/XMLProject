import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class WorkService {

  constructor(private http: HttpClient) { }

  getWorks(): Promise<any>{
    const url = '/api/naucni_radovi/odobreno';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  myWorks(): Promise<any>{
    const url = '/api/naucni_radovi/moji';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  newWorks(): Promise<any>{
    const url = '/api/naucni_radovi/poslati';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  progressWorks(): Promise<any>{
    const url = '/api/naucni_radovi/u_proceduri';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addReview(work: WorkInterface): Promise<any>{
    const url = '/api/naucni_radovi/recenzent';
    return this.http
          .post(url, work)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }


  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

}
