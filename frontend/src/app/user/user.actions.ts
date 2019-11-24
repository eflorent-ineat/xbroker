import { props, createAction } from '@ngrx/store';
import { User, Credentials } from './user.model';


export const ACTION_TOKEN_SUCCESS  = '[User/API] Token Success';
export const ACTION_LOGIN_SUCCESS  = '[User/API] Login Success';
export const ACTION_LOGIN_FAILURE  = '[User/API] Login Failure';
export const ACTION_LOGOUT_CONFIRMATION  = '[User/API] Logout confirmation';
export const ACTION_LOGOUT_DISMISS  = '[User/API] Logout dissmiss';
export const ACTION_LOGOUT  = '[User/API] Logout';

export const tokenSuccess = createAction(
    ACTION_TOKEN_SUCCESS,
    props<{ credentials: Credentials}>()
);

export const loginSuccess = createAction(ACTION_LOGIN_SUCCESS, props<{ user: User }>());

export const loginFailure = createAction(ACTION_LOGIN_FAILURE,  props<{ error: any }>());

export const logoutConfirmationDismiss = createAction(ACTION_LOGOUT_CONFIRMATION);

export const logoutConfirmation = createAction(ACTION_LOGOUT_CONFIRMATION);

export const logout = createAction(ACTION_LOGOUT);