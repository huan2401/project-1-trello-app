import React from "react";
import { useState, useEffect } from "react";
import "./Login.scss";
import { Link } from "react-router-dom";
const Login = () => {
    const initialValues = { username: "", password: "" };
    const [formValues, setFormValues] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setFormErrors(validate(formValues));
        setIsSubmit(true);
    };

    useEffect(() => {
        console.log(formErrors);
        if (Object.keys(formErrors).length === 0 && isSubmit) {
            console.log(formValues);
        }
    }, [formErrors]);
    const validate = (values) => {
        const errors = {};
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
        if (!values.username) {
            errors.username = "Username is required!";
        }
        // if (!values.email) {
        //     errors.email = "Email is required!";
        // } else if (!regex.test(values.email)) {
        //     errors.email = "This is not a valid email format!";
        // }
        if (!values.password) {
            errors.password = "Password is required";
        } else if (values.password.length < 4) {
            errors.password = "Password must be more than 4 characters";
        } else if (values.password.length > 10) {
            errors.password = "Password cannot exceed more than 10 characters";
        }
        return errors;
    };
    return (
        <section>
            {Object.keys(formErrors).length === 0 && isSubmit ? (
                <div className="ui message success">Signed in successfully</div>
            ) : (
                <pre>{JSON.stringify(formValues, undefined, 2)}</pre>
            )}
            <form onSubmit={handleSubmit}>
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
                    {/* <div className="square" style={{ "--i": 0 }}></div>
                    <div className="square" style={{ "--i": 1 }}></div>
                    <div className="square" style={{ "--i": 2 }}></div>
                    <div className="square" style={{ "--i": 3 }}></div>
                    <div className="square" style={{ "--i": 4 }}></div> */}
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
                                        value={formValues.username}
                                        onChange={handleChange}
                                    />
                                </div>
                                <p>{formErrors.username}</p>
                                <div className="inputBox">
                                    <label htmlFor="">Password</label>
                                    <input
                                        type="text"
                                        name="password"
                                        placeholder="Password"
                                        value={formValues.password}
                                        onChange={handleChange}
                                    />
                                </div>
                                <p>{formErrors.password}</p>
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
            </form>
        </section>
    );
};

export default Login;
