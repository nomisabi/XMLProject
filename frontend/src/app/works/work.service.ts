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

  addReview(id: string, idRevision: string, revision: RevisionInterface): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/recenzent`;
    return this.http
          .post(url, revision)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getWork(id: string):Promise<any>{
    const url = `/api/naucni_radovi/${id}`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getWorkForReview(id: string, idRevision: string):Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  deleteWork(id: string, idRevision: string):Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}`;
    return this.http
          .delete(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getWorksForReviewer(): Promise<any>{
    const url = `/api/naucni_radovi/dodeljeni`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  acceptReview(id: string, idRevision: string): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/prihvati`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  disardReview(id: string, idRevision: string): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/odbi`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }


  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

}
