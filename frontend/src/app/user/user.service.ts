import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError as observableThrowError } from 'rxjs';
import { catchError, mapTo, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from './user.model';
import * as UserActions from './user.actions';
const TOKEN = 'TOKEN';
const REFRESH_TOKEN = 'REFRESH_TOKEN';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userState: any;

  environment = environment;

  constructor(private http: HttpClient) {}

  setToken(token: string): void {
    localStorage.setItem(TOKEN, token);
  }

  getUserInfo(token: string) {
    this.setToken(token);
    return this.http.get("/api/v2/userinfo");
  }

  getToken() {
    return localStorage.getItem(TOKEN);
  }


  isLogged() {
    return localStorage.getItem(TOKEN) != null;
  }

  doSignOut() {
      localStorage.removeItem(TOKEN);
  }

  logout() {
    return this.http.get<any>(`${environment.backendUrl}/logout`).pipe(
      tap(() => this.doSignOut()),
      mapTo(true),
      catchError(error => {
        alert(error.error.message);
        return of(false);
      }));
    }
}

