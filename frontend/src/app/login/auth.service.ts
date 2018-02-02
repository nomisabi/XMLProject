import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AuthService {
  private authUrl = '/api/login';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Promise<any>{
    return this.http
          .post(this.authUrl, {username, password}, {responseType: 'text'})
          .toPromise()
          .then(res => localStorage.setItem('token', res))
          .catch(this.handleError);
  }

  register(korisnik: string): Promise<any>{
    return this.http
          .post('/api/korisnici/novi', korisnik)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
}
}
