import { useEffect, useState } from "react";
import React from "react";
import "./Setting.scss";
import Button from "@mui/material/Button";
import { TextField, Input } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { Form, Input as InputAnt, Radio, Button as ButtonAnt } from "antd";
import { changePassword, updateProfile } from "slices/authSlice";
import { toast } from "react-toastify";

const Setting = () => {
  const dispatch = useDispatch();
  const [formChangePassword] = Form.useForm();
  const user = useSelector((state) => state.auth.user);
  const navigate = useNavigate();
  const [avatar, setAvatar] = useState();
  const [dataPost, setDataPost] = useState({
    email: "",
    username: "",
    firstName: "",
    lastName: "",
    passWord: "",
    previousPassword: "",
  });

  console.log("user", user);

  useEffect(() => {
    window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
  }, []);

  useEffect(() => {
    return () => {
      avatar && URL.revokeObjectURL(avatar.preview);
    };
  }, [avatar]);

  const handlePreviewAvatar = (e) => {
    const file = e.target.files[0];
    file.preview = URL.createObjectURL(file);
    setAvatar(file);
  };
  const handleEventClick = (e) => {
    if (dataPost.password === dataPost.previousPassword) console.log(dataPost);

    const values = Object.keys(dataPost).reduce((object, key) => {
      if (key !== "password" && key !== "previousPassword") {
        object[key] = dataPost[key];
      }
      return object;
    }, {});

    dispatch(updateProfile(values)).then((res) => {
      console.log("res update profile", res);
    });
  };
  const handleOnchange = (event, field) => {
    setDataPost((prev) => {
      return {
        ...prev,
        [field]: event.target.value,
      };
    });
  };

  const onFinish = (values) => {
    console.log("Success:", values);
    for (let key in values) {
      if (!values[`${key}`]) {
        values[`${key}`] = user[`${key}`];
      }
    }
    dispatch(updateProfile(values)).then((res) => {
      if (res.payload.user.status === 200) {
        toast.success("Update profile success", { autoClose: 1000 });
        window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
      }
    });
  };

  const onFinishChangePassword = (values) => {
    console.log("Success:", values);
    dispatch(changePassword(values)).then((res) => {
      console.log("abc", res);
      if (res.payload.user.status === 200) {
        toast.success("Change password success", { autoClose: 1000 });
        formChangePassword.resetFields();
        window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
      } else if (res.payload.user.status === 204) {
        toast.error("Previous Password invalid", { autoClose: 2000 });
      }
    });
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  const onFinishFailedChangePassword = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  console.log("dataPost", dataPost);
  return (
    <div className="setting">
      <div className="setting__avatar">
        <p>Update Avatar</p>
        <label htmlFor="contained-button-file">
          <Input
            accept="image/*"
            id="contained-button-file"
            multiple
            type="file"
            onChange={handlePreviewAvatar}
            sx={{ display: "none" }}
          />
          <Button
            sx={{ marginBottom: "20px" }}
            variant="contained"
            component="span"
          >
            Attachment
          </Button>
        </label>
        <label htmlFor="icon-button-file">
          <Input
            accept="image/*"
            id="icon-button-file"
            type="file"
            onChange={handlePreviewAvatar}
            sx={{ display: "none" }}
          />
          <IconButton
            color="primary"
            aria-label="upload picture"
            component="span"
          >
            <PhotoCamera sx={{ marginBottom: "20px" }} />
          </IconButton>
        </label>
        <div>{avatar && <img src={avatar.preview} alt="" width="10%" />}</div>
        <Button sx={{ width: "100px", fontWeight: "600" }} variant="outlined">
          Upload
        </Button>
      </div>
      <div className="setting__detail">
        <div className="setting__detail-more1">
          <h3>Account Details</h3>
          <div className="language">
            <p>Language: </p>
            <Button
              sx={{
                marginLeft: "20px",
                marginRight: "20px",
                fontWeight: "600",
              }}
              color="secondary"
            >
              Tiếng Việt
            </Button>
            <Button color="success" sx={{ fontWeight: "600" }}>
              English
            </Button>
          </div>
        </div>

        {/* <div className="setting__detail-more2">
          <h3>Email</h3>
          <p>Enter Email: </p>
          <TextField
            label="New Email"
            variant="outlined"
            value={dataPost.email}
            onChange={(e) => handleOnchange(e, "email")}
          />
        </div>
        <div className="setting__detail-more3">
          <h3>Username</h3>
          <p>Enter Username: </p>
          <TextField
            label="New Username"
            variant="outlined"
            value={dataPost.username}
            onChange={(e) => handleOnchange(e, "username")}
          />
        </div>
        <div className="setting__detail-more3">
          <h3>Fullname</h3>
          <p>Enter Fullname: </p>
          <TextField
            label="First Name"
            variant="outlined"
            value={dataPost.firstName}
            onChange={(e) => handleOnchange(e, "firstName")}
          />
          <TextField
            sx={{ marginLeft: "30px" }}
            label="Last Name"
            variant="outlined"
            value={dataPost.lastName}
            onChange={(e) => handleOnchange(e, "lastName")}
          />
        </div>
        <div className="setting__detail-more3">
          <h3>Sex</h3>
          <TextField
            label="Male"
            variant="outlined"
            value={dataPost.sex}
            onChange={(e) => handleOnchange(e, "sex")}
          />
        </div>
        <div className="setting__detail-more4">
          <h3>Change Password</h3>
          <p>Enter Password: </p>
          <div className="test">
            <TextField
              sx={{ marginLeft: "30px" }}
              type="password"
              label="Previous Password"
              variant="outlined"
              value={dataPost.previousPassword}
              onChange={(e) => handleOnchange(e, "previousPassword")}
            />
            <TextField
              label="Password"
              type="password"
              variant="outlined"
              value={dataPost.password}
              onChange={(e) => handleOnchange(e, "password")}
            />
          </div>
        </div> */}
        <div className="setting-updateProfile">
          <p>Update Profile</p>
          <Form
            name="update"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
          >
            <Form.Item
              label="First Name"
              name="firstName"
              rules={[
                {
                  message: "Please input your firstname!",
                },
              ]}
            >
              <InputAnt defaultValue={user ? user.firstName : ""} />
            </Form.Item>
            <Form.Item
              label="Last Name"
              name="lastName"
              rules={[
                {
                  message: "Please input your lastname!",
                },
              ]}
            >
              <InputAnt defaultValue={user ? user.lastName : ""} />
            </Form.Item>

            <Form.Item name="sex" label="Sex">
              <Radio.Group defaultValue={user.sex}>
                <Radio value={"Male"}>Male</Radio>
                <Radio value={"Female"}>Female</Radio>
              </Radio.Group>
            </Form.Item>

            <Form.Item
              label="Phone"
              name="phoneNumber"
              rules={[
                {
                  message: "Please input your phone!",
                },
              ]}
            >
              <InputAnt defaultValue={user ? user.phoneNumber : ""} />
            </Form.Item>

            <Form.Item
              wrapperCol={{
                offset: 8,
                span: 16,
              }}
            >
              <ButtonAnt type="primary" htmlType="submit">
                Update Profile
              </ButtonAnt>
            </Form.Item>
          </Form>
        </div>

        <div className="setting-changePassword">
          <p>Change Password</p>
          <Form
            name="changePassword"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            onFinish={onFinishChangePassword}
            onFinishFailed={onFinishFailedChangePassword}
            autoComplete="off"
            form={formChangePassword}
          >
            <Form.Item
              label="Password Old"
              name="previousPassword"
              rules={[
                {
                  required: true,
                  message: "Please input your previousPassword!",
                },
              ]}
            >
              <InputAnt.Password />
            </Form.Item>

            <Form.Item
              label="Password New"
              name="passWord"
              rules={[
                {
                  required: true,
                  message: "Please input your password new!",
                },
              ]}
            >
              <InputAnt.Password />
            </Form.Item>
            <Form.Item
              wrapperCol={{
                offset: 8,
                span: 16,
              }}
            >
              <ButtonAnt type="primary" htmlType="submit">
                Change Password
              </ButtonAnt>
            </Form.Item>
          </Form>
        </div>

        {/* <div>
          <Button
            sx={{ marginTop: "30px" }}
            variant="contained"
            endIcon={<SendIcon />}
            onClick={handleEventClick}
          >
            Save
          </Button>
        </div>
        <div className="setting__detail-more5">
          <Button
            className="logout"
            variant="contained"
            onClick={() => {
              navigate("/login");
            }}
          >
            Log Out
          </Button>
        </div> */}
      </div>
    </div>
  );
};

export default Setting;
