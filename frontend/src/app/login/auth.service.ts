import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';


@Injectable()
export class AuthService {
  private roles: string[];

  constructor(private http: HttpClient,
              private router: Router) { }

  login(username: string, password: string): Promise<any>{
    const url = '/api/login';
    return this.http
          .post(url, {username, password}, {responseType: 'text'})
          .toPromise()
          .then(res => {
            localStorage.setItem('token', res);
            this.setRoles();
          })
          .catch(this.handleError);
  }

  register(korisnik: string): Promise<any>{
    const url = '/api/korisnici/novi';
    var httpHeaders = new HttpHeaders({"Content-Type": 'application/xml'});
    return this.http
          .post(url, korisnik, {headers: httpHeaders })
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getToken(): String {
    const token = localStorage.getItem('token');
    return token ? token : "";
  }

  getCurrentUser(): string {
    const token = localStorage.getItem('token');
    if (token){
      const tokenPayload = decode(token);
      const username = tokenPayload.sub;
      return username;
    }
    return "";
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['']);
  }

  isEditor(): boolean {
    return this.roles.includes('UREDNIK');
  }

  isAuthor(): boolean {
    return this.roles.includes('AUTOR');
  }

  isReviewer(): boolean {
    return this.roles.includes('RECENZENT');
  }

  private setRoles(): void {
    const token = localStorage.getItem('token');
    const tokenPayload = decode(token);
    this.roles = tokenPayload.scopes;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
