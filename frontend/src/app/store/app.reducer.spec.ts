import { appReducer, initializeApp } from './app.reducer';
import { initialAppState } from './app.state';

describe('AppReducer', () => {
  it('should return the initial state', () => {
    const result = appReducer(undefined, { type: 'unknown' });
    expect(result).toEqual(initialAppState);
  });

  it('should set initialized to true on initializeApp action', () => {
    const result = appReducer(initialAppState, initializeApp());
    expect(result.initialized).toBeTrue();
  });
});
