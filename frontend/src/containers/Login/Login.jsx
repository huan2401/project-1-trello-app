import React from "react";
import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { checkLogin, login } from "slices/authSlice";
import { Navigate } from "react-router-dom";
import "./Login.scss";
import { toast } from "react-toastify";

function Login() {
  const initialValues = { username: "", password: "" };
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);
  const navigate = useNavigate();

  const isLogin = useSelector((state) => state.auth.login);
  const dispatch = useDispatch();
  useEffect(() => {
    if (isLogin) {
      navigate("/", { replace: true });
    }
  }, [isLogin]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmit(true);
    dispatch(login(formValues)).then((res) => {
      console.log("res login", res);
      if (!res.payload) {
        toast.error("Wrong username/email or password !", {
          autoClose: 2000,
        });
      }
      toast.success("Login success", {
        autoClose: 2000,
      });
    });
  };

  useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmit) {
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
    <div className="login">
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
            <h2>Login</h2>
            <div>
              <div className="inputBox">
                <label>Username</label>
                <input
                  type="text"
                  name="username"
                  placeholder="Username"
                  value={formValues.username}
                  onChange={handleChange}
                />
              </div>
              <p className="message">{formErrors.username}</p>

              <div className="inputBox">
                <label>Password</label>
                <input
                  type="password"
                  name="password"
                  placeholder="Password"
                  value={formValues.password}
                  onChange={handleChange}
                />
              </div>
              <p className="message">{formErrors.password}</p>
              <div className="inputBox">
                <input type="submit" value="Login" />
              </div>
              <p className="forget">
                Forgot Password ?<a href="#">Click Here</a>
              </p>
              <p className="forget">
                Don't have an account ?<Link to="/signup">Sign up</Link>
              </p>
            </div>
          </div>
        </div>
      </form>
    </div>
  );
}

export default Login;
