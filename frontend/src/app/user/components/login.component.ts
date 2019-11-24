import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Title }     from '@angular/platform-browser';
import { Store } from '@ngrx/store';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from '../user.service'
import * as UserActions from '../user.actions';
import { User } from '../user.model';

const TOKEN = 'token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  environment = environment;
  componentTitle : string = "Login"

  constructor(  private titleService: Title,
                private userService: UserService,
                private cookieService: CookieService,
                private store: Store<{ user: User }>
                ) { }

  ngOnInit() {
        this.titleService.setTitle(this.componentTitle)
        const currentURL = window.location.href;
        // workaround for redirecting to developement front after extenal SSO
        // reason is that remote SSO authority register the backend and will not accept redirect frontend
        // registering frontend would not have worked thus...
        if (currentURL == 'http://localhost:8080/') {
            window.location.href='http://localhost:4200/'
        } else {
                const token = this.cookieService.get(TOKEN)
                if (token) {
                    this.store.dispatch({ type: UserActions.ACTION_TOKEN_SUCCESS, credentials: {token}  });
                }
        }
    }





}
