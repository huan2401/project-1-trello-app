import React from "react";
import { useState, useEffect } from "react";
import "./Login.scss";
import { Link } from "react-router-dom";
const Login = () => {
    return (
        <section className="login">
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
                <div className="square" style={{ "--i": 2 }}></div>
                <div className="square" style={{ "--i": 3 }}></div>
                <div className="square" style={{ "--i": 4 }}></div>
                <div className="container">
                    <div className="form">
                        <h2>Login</h2>
                        <form>
                            <div className="inputBox">
                                <label htmlFor="">Username</label>
                                <input
                                    type="text"
                                    name="username"
                                    placeholder="Username"
                                />
                            </div>
                            <div className="inputBox">
                                <label htmlFor="">Password</label>
                                <input
                                    type="text"
                                    name="password"
                                    placeholder="Password"
                                />
                            </div>
                            <div className="inputBox">
                                <input type="submit" value="Login" />
                            </div>
                            <p className="forget">
                                Forgot Password ?<a href="#">Click Here</a>
                            </p>
                            <p className="forget">
                                Don't have an account ?
                                <Link to="/signup">Sign up</Link>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Login;
