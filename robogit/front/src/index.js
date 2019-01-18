import React from 'react';
import ReactDOM from 'react-dom';
import {createStore, applyMiddleware} from 'redux';
import {composeWithDevTools} from 'redux-devtools-extension';
import thunk from 'redux-thunk';
import {syncHistoryWithStore} from 'react-router-redux';
import {Router, Route, browserHistory} from 'react-router';
import {Provider} from 'react-redux';
import { Switch } from 'react-router-dom';

import reducers from 'reducers';
import Layout from 'containers/layoyt';
import Products from 'containers/products';
import Platforms from 'containers/platforms';

import { createBrowserHistory } from 'history';

const store = createStore(reducers, composeWithDevTools(
    applyMiddleware(thunk)
));
const history = syncHistoryWithStore(createBrowserHistory(), store);


ReactDOM.render(
    <Provider store={store}>
        <Router history={history}>
            <Layout>
                <Switch>
                    <Route path='/' component={Products} />
                    <Route path='/platforms' component={Platforms} />
                </Switch>
            </Layout>
        </Router>
    </Provider>,
    document.getElementById('app')
);