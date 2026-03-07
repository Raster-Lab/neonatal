import { createReducer, on, createAction } from '@ngrx/store';
import { AppState, initialAppState } from './app.state';

export const initializeApp = createAction('[App] Initialize');

export const appReducer = createReducer(
  initialAppState,
  on(initializeApp, (state): AppState => ({ ...state, initialized: true }))
);
