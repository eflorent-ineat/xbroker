import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable } from 'rxjs';
import { UserService } from '../user/user.service'

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor {

    constructor(private userService: UserService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    // Get the auth token from the service.
    const authToken = this.userService.getToken();
    console.log(authToken);
    // Clone the request and replace the original headers with
    // cloned headers, updated with the authorization.
    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + authToken)
    });
    // send cloned request with header to the next handler.
    return next.handle(authReq);
  }
}