
import { createReducer, on } from '@ngrx/store';
import * as UserActions from './user.actions';
import { User } from './user.model';

export const statusFeatureKey = 'status';

export interface State {
  user: User | null;
}

export const initialState: State = {
  user: null,
};

export const reducer = createReducer(
  initialState,
  on(UserActions.loginSuccess, (state, user ) => ({ ...state, user })),
  on(UserActions.logout, () => initialState),
);

export const getUser = (state: State) => state.user;