import "./App.less";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "containers/Home";
import PrivateRoute from "components/common/PrivateRoute";
import Login from "components/common/Login";
import Signup from "components/common/Signup";

function App() {
    return (
        <Router>
            <Switch>
                <Route path="/login">
                    <Login />
                </Route>
                <Route path="/signup">
                    <Signup />
                </Route>
                <PrivateRoute path="/" Component={Home} />
            </Switch>
        </Router>
    );
}

export default App;
