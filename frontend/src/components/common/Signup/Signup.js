import React from "react";
import "./Signup.scss";
import { Link } from "react-router-dom/cjs/react-router-dom.min";
const Signup = () => {
    return (
        <section className="signup">
            <div className="logo">
                <img
                    src="https://aid-frontend.prod.atl-paas.net/atlassian-id/front-end/5.0.332/static/media/trello-logo-blue.f7627b3a.svg"
                    alt="Trello Logo"
                />
            </div>
            <div className="color"></div>
            <div className="color"></div>
            <div className="color"></div>
            <div className="box">
                <div className="square" style={{ "--i": 0 }}></div>
                <div className="square" style={{ "--i": 1 }}></div>
                {/* <div className="square" style={{ "--i": 2 }}></div> */}
                {/* <div className="square" style={{ "--i": 3 }}></div> */}
                <div className="square" style={{ "--i": 4 }}></div>
                <div className="container">
                    <div className="form">
                        <h2>Sign Up</h2>
                        <form>
                            <div className="inputBox">
                                <input type="text" placeholder="Username" />
                            </div>
                            <div className="inputBox">
                                <input type="email" placeholder="Email" />
                            </div>
                            <div className="inputBox">
                                <input type="password" placeholder="Password" />
                            </div>
                            <div className="inputBox">
                                <input
                                    type="password"
                                    placeholder="Confirm Password"
                                />
                            </div>
                            <div className="inputBox">
                                <input type="submit" value="Sign Up" />
                            </div>
                            <p className="forget1">
                                Forgot Password ?<a href="#">Click Here</a>
                            </p>
                            <p className="forget2">
                                <Link to="/login">Log In</Link>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Signup;
