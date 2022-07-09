import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Signup.scss";

function App() {
    const initialValues = {
        username: "",
        email: "",
        password: "",
        confirm: "",
    };
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
        if (!values.email) {
            errors.email = "Email is required!";
        } else if (!regex.test(values.email)) {
            errors.email = "This is not a valid email format!";
        }
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
        <div className="signup">
            <div className="color"></div>
            <div className="color"></div>
            <div className="color"></div>
            <form onSubmit={handleSubmit} className="box">
                <div className="square" style={{ "--i": 0 }}></div>
                <div className="square" style={{ "--i": 1 }}></div>
                <div className="square" style={{ "--i": 2 }}></div>
                <div className="square" style={{ "--i": 3 }}></div>
                <div className="square" style={{ "--i": 4 }}></div>

                <div className="container">
                    <div className="form">
                        <h2>Sign Up</h2>
                        <div>
                            {/* Username */}
                            <div className="inputBox">
                                {/* <label>Username</label> */}
                                <input
                                    type="text"
                                    name="username"
                                    placeholder="Username"
                                    value={formValues.username}
                                    onChange={handleChange}
                                />
                            </div>
                            <p className="message">{formErrors.username}</p>

                            {/* Email */}

                            <div className="inputBox">
                                {/* <label>Email</label> */}
                                <input
                                    type="text"
                                    name="email"
                                    placeholder="Email"
                                    value={formValues.email}
                                    onChange={handleChange}
                                />
                            </div>
                            <p className="message">{formErrors.email}</p>

                            {/* Password */}

                            <div className="inputBox">
                                {/* <label>Password</label> */}
                                <input
                                    type="password"
                                    name="password"
                                    placeholder="Password"
                                    value={formValues.password}
                                    onChange={handleChange}
                                />
                            </div>
                            <p className="message">{formErrors.password}</p>

                            {/* Confirm Password */}

                            <div className="inputBox">
                                {/* <label>Confirm Password</label> */}
                                <input
                                    type="password"
                                    name="confirm"
                                    placeholder="Confirm Password"
                                    value={formValues.confirm}
                                    onChange={handleChange}
                                />
                            </div>
                            <p className="message">{formErrors.password}</p>

                            {/* Detail */}

                            <div className="inputBox">
                                <input type="submit" value="Sign Up" />
                            </div>

                            <p className="forget1">
                                Forgot Password ?<a href="#">Click Here</a>
                            </p>
                            <p className="forget2">
                                <Link to="/login">Log In</Link>
                            </p>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default App;