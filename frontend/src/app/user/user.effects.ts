import { Injectable } from '@angular/core';
import { Actions, ofType, createEffect } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, exhaustMap, map } from 'rxjs/operators';
import * as UserActions from './user.actions';
import { tokenSuccess } from './user.actions';
import { User } from './user.model';
import { UserService } from './user.service';
import { Credentials } from './user.model';

@Injectable()
export class UserEffects {

  constructor(
    private actions$: Actions,
    private userService: UserService
  ) {}

  verifyToken$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.tokenSuccess),
        exhaustMap(action =>
        this.userService.getUserInfo(action.credentials.token).pipe(
          map(user => UserActions.loginSuccess({ user } )),
          catchError(error => of(UserActions.loginFailure({ error })))
        )
      )
    )
  );

  recordUser$ = createEffect(() =>
      this.actions$.pipe(
        ofType(UserActions.loginSuccess),
          exhaustMap(action => this.userService.setUserInfo(action)
          )
        )

    );



}