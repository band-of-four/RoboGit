import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';

import products from './products';
import platforms from './platforms'

export default combineReducers({
  routing: routerReducer,
  products,
  platforms
})