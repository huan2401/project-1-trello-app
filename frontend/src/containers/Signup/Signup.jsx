import { Modal, Input } from "antd";
import React from "react";
import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { register } from "slices/authSlice";
import axiosClient from "utils/axiosClient";
import "./Signup.scss";
// import { injectStyle } from "react-toastify/dist/inject-style";
import { ToastContainer, toast } from "react-toastify";

// if (typeof window !== "undefined") {
//   injectStyle();
// }

function isEmptyObject(obj) {
  return JSON.stringify(obj) === "{}";
}

function App() {
  const initialValues = {
    userName: "",
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirm: "",
    sex: "",
  };
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [verificationCode, setVerificationCode] = useState(false);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    console.log("verificationCode", verificationCode);
    if (verificationCode) {
      axiosClient
        .get("/auth/verify-by-link", {
          params: {
            verification_code: verificationCode,
          },
        })
        .then((res) => {
          if (res.status === 200) {
            toast.success("Active success !", { autoClose: 2000 });
            setIsModalVisible(false);
            navigate("/login");
          }
        });
    }
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  console.log("signup data", formValues);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmit(true);

    const values = Object.keys(formValues).reduce((object, key) => {
      if (key !== "confirm") {
        object[key] = formValues[key];
      }
      return object;
    }, {});

    console.log("values", values);
    dispatch(register(values))
      .then((res) => {
        console.log("res", res);
        if (
          res.payload.status === "success" &&
          res.payload.statusCode === 200
        ) {
          toast.warning("Please enter active code", {
            autoClose: 2000,
          });
          showModal();
        } else if (!res.payload) {
          toast.error("Wrong username/email", {
            autoClose: 2000,
          });
        }
      })
      .catch((err) => {
        toast.warning("Register fail !", { autoClose: 2000 });
      });
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
    if (!values.userName) {
      errors.userName = "Username is required!";
    }
    if (!values.firstName) {
      errors.firstName = "First Name is required!";
    }
    if (!values.lastName) {
      errors.lastName = "Last Name is required!";
    }
    if (!values.sex) {
      errors.sex = "Sex is required!";
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
    if (!values.confirm) {
      errors.confirm = "Confirm password is required";
    }
    if (values.password && values.confirm) {
      if (values.password !== values.confirm) {
        errors.confirm = "Confirm password and password not match";
      }
    }
    return errors;
  };

  return (
    <>
      <div className="signup">
        <div className="color"></div>
        <div className="color"></div>
        <div className="color"></div>
        <form onSubmit={handleSubmit} className="box">
          <div className="square" style={{ "--i": 0 }}></div>
          <div className="square" style={{ "--i": 1 }}></div>
          {/* <div className="square" style={{ "--i": 2 }}></div> */}
          {/* <div className="square" style={{ "--i": 3 }}></div> */}
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
                    name="userName"
                    placeholder="Username"
                    value={formValues.userName}
                    onChange={handleChange}
                  />
                </div>
                <p className="message">{formErrors.userName}</p>

                {/* First Name */}
                <div className="inputBox">
                  <input
                    type="text"
                    name="firstName"
                    placeholder="First Name"
                    value={formValues.firstName}
                    onChange={handleChange}
                  />
                </div>
                <p className="message">{formErrors.firstName}</p>

                {/* Last Name */}
                <div className="inputBox">
                  <input
                    type="text"
                    name="lastName"
                    placeholder="Last Name"
                    value={formValues.lastName}
                    onChange={handleChange}
                  />
                </div>
                <p className="message">{formErrors.lastName}</p>

                {/* Sex */}
                <div className="inputBox">
                  <input
                    type="text"
                    name="sex"
                    placeholder="Sex"
                    value={formValues.sex}
                    onChange={handleChange}
                  />
                </div>
                <p className="message">{formErrors.lastName}</p>

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

                <div
                  className="inputBox"
                  onClick={() => {
                    //   if (isEmptyObject(formErrors)) {
                    //     console.log("first");
                    //     showModal();
                    //   }
                  }}
                >
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
        <Modal
          title="Enter the code"
          visible={isModalVisible}
          onOk={handleOk}
          onCancel={handleCancel}
        >
          <p>Please go to your email to activate your account</p>
          <Input
            placeholder="Enter the code"
            onChange={(e) => setVerificationCode(e.target.value)}
          />
        </Modal>
      </div>
      {/* <ToastContainer /> */}
    </>
  );
}

export default App;
