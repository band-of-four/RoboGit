import React from 'react'
import ReactDOM from 'react-dom'
import {createStore, applyMiddleware} from 'redux'
import {composeWithDevTools} from 'redux-devtools-extension'
import thunk from 'redux-thunk'
import {syncHistoryWithStore} from 'react-router-redux'
import {Router, Route, browserHistory} from 'react-router'
import {Provider} from 'react-redux'

import reducers from 'reducers'
import Layout from 'containers/layout'
import Informations from 'containers/informations'
import Information from 'containers/information'
import Basket from 'containers/basket'

const store = createStore(reducers, composeWithDevTools(
  applyMiddleware(thunk)
));

const history = syncHistoryWithStore(browserHistory, store);
console.log("!");

ReactDOM.render(
  <Provider store={store}>
    <Router history={history}>
      <Route component={Layout}>
        <Route path='/' component={Informations}/>
        <Route path='/categories/:id' component={Informations}/>
      </Route>
      <Route path='/informations/:id' component={Information}/>
      <Route path='/basket' component={Basket} />
    </Router>
  </Provider>,
  document.getElementById('root')
);
