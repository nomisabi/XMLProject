import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getReviews(id: string, idRevision: string): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/recenzenti`;
    var httpHeaders = new HttpHeaders({"Content-Type": 'application/xml'});
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
