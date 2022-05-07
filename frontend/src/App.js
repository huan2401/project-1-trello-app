import './App.less';
import {BrowserRouter as Router,Switch,Route} from 'react-router-dom';
import Home from "containers/Home";
import PrivateRoute from "components/common/PrivateRoute";
import Login from "components/common/Login";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/login">
          <Login/>
        </Route>
        <PrivateRoute path="/" Component={Home}/>
      </Switch>
    </Router>
  );
}

export default App;
