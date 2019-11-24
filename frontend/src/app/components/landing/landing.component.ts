import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../../user/user.service';
import * as UserActions from '../../user/user.actions';
import { Store, select } from '@ngrx/store';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../../user/user.model';
import {getUser} from'../../user/user.reducers';

const TOKEN = 'token';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  @Input() user$: any;

  constructor(  private userService: UserService,
                private store: Store<User>,
                private cookieService: CookieService
                ) { }

  ngOnInit() {
        const token = this.cookieService.get(TOKEN)
        if (token) {
            this.store.dispatch({ type: UserActions.ACTION_TOKEN_SUCCESS, credentials: {token}  });
        }
        this.user$ = this.store.pipe(select((state: State) => state.user))
  }

}
