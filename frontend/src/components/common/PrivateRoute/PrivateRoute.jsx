import React from 'react';
import {Route} from 'react-router-dom';

const PrivateRoute = ({Component, ...rest}) => {

  return (
    <Route
      {...rest}
      render={routeProps => Component && <Component {...routeProps} />}
    />
  )
}

export default PrivateRoute;