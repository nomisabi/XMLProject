import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Response, RequestOptions, ResponseContentType } from '@angular/http';
import { Headers } from '@angular/http/src/headers';

@Injectable()
export class WorkService {
  headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token')});

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

  getMyWorksForReviewer(): Promise<any>{
    const url = `/api/naucni_radovi/prihvaceni`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getMyREc(id: string, idRevision: string): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/recenzije`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addRec(recenzija: any, id: string, idRevision): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/recenzije`;
    return this.http
          .post(url, recenzija)
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

  publishRevision(id: string, idRevision: string): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/objavi`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  reviseRevision(id: string, idRevision: string): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/potrebna_izmena`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  rejectRevision(id: string, idRevision: string): Promise<any>{
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/odbaci`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  search(param: string): Promise<any>{
    const url = `/api/naucni_radovi`;
    const httpParams = new HttpParams().set('param', param);
    return this.http
          .get(url, {params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }


  searchForm(form: SearchForm): Promise<any>{
    const url = `/api/naucni_radovi/search`;
    return this.http
          .post(url, form)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  searchFormAuthor(form: SearchForm): Promise<any>{
    const url = `/api/naucni_radovi/search/moji`;
    return this.http
          .post(url, form)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  searchAuthor(param: string): Promise<any>{
    const url = `/api/naucni_radovi/moji`;
    const httpParams = new HttpParams().set('param', param);
    return this.http
          .get(url, {params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getRevizija(id: string, idRevision: string): Promise<RevizijaInterface>{
    console.log('service')
    const url = `/api/naucni_radovi/${id}/revizija/${idRevision}/html`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }


  getPdf(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'application/pdf','Content-Type':'application/pdf'});
    
    const url = `/api/naucni_radovi/${id}/download`;
    return this.http
          .get(url, {headers:httpHeaders, responseType:'blob' })
          .toPromise()
          .then(res => res)
          .catch(this.handleError);

          
  }

  getHtml(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'text/html;charset=UTF-8','Content-Type':'text/html;charset=UTF-8'});
    const url = `/api/naucni_radovi/${id}/html`;
    return this.http
          .get(url, {headers:httpHeaders, responseType: 'text'})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getXml(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'application/xml;charset=UTF-8','Content-Type':'application/xml;charset=UTF-8'});
    const url = `/api/naucni_radovi/${id}.xml`;
    return this.http
          .get(url, {headers:httpHeaders, responseType: 'text'})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getPdfNoAuthor(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'application/pdf','Content-Type':'application/pdf'});
    
    const url = `/api/naucni_radovi/${id}/no_author/download`;
    return this.http
          .get(url, {headers:httpHeaders, responseType:'blob' })
          .toPromise()
          .then(res => res)
          .catch(this.handleError);

          
  }

  getHtmlNoAuthor(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'text/html;charset=UTF-8','Content-Type':'text/html;charset=UTF-8'});
    const url = `/api/naucni_radovi/${id}/no_author/html`;
    return this.http
          .get(url, {headers:httpHeaders, responseType: 'text'})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getPdfPismo(id: string, revId:string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'application/pdf','Content-Type':'application/pdf'});
    
    const url = `/api/naunci_rad/${id}/revizija/${revId}/protpatnoPismo/download`;
    return this.http
          .get(url, {headers:httpHeaders, responseType:'blob' })
          .toPromise()
          .then(res => res)
          .catch(this.handleError);

          
  }

  getPdfRef(id: string, revId:string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'application/pdf','Content-Type':'application/pdf'});
    
    const url = `/api/naunci_rad/${id}/revizija/${revId}/recenzije/download`;
    return this.http
          .get(url, {headers:httpHeaders, responseType:'blob' })
          .toPromise()
          .then(res => res)
          .catch(this.handleError);

          
  }

  getHtmlPismo(id: string, revId:string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'text/html;charset=UTF-8','Content-Type':'text/html;charset=UTF-8'});
    const url = `/api/naunci_rad/${id}/revizija/${revId}/protpatnoPismo/html`;
    return this.http
          .get(url, {headers:httpHeaders, responseType: 'text'})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addRdf(id: string, rdf:string):Promise<any>{
    const url = `/api/naucni_radovi/${id}/rdf`;
    return this.http
          .post(url, rdf)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getRdfJSON(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'application/json','Content-Type':'application/json'});
    
    const url = `/api/naucni_radovi/${id}/json`;
    return this.http
          .get(url, {headers:httpHeaders, responseType: 'json'})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }
  
  getRdfRDF(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'text/plain;charset=UTF-8','Content-Type':'text/plain;charset=UTF-8'});
    const url = `/api/naucni_radovi/${id}/rdf`;
    return this.http
          .get(url, {headers:httpHeaders, responseType: 'text'})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getLinks(id: string):Promise<any>{
    let httpHeaders:HttpHeaders = new HttpHeaders({"Accept": 'application/json','Content-Type':'application/json'});
    
    const url = `/api/naucni_radovi/${id}/links`;
    return this.http
          .get(url, {headers:httpHeaders, responseType: 'json'})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }
  

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

}
