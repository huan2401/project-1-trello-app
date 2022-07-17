import { useEffect, useState } from "react";
import React from "react";
import "./Setting.scss";
import Button from "@mui/material/Button";
import { Input, TextField } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

const Setting = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [avatar, setAvatar] = useState();
    const [dataPost, setDataPost] = useState({
        email: "",
        username: "",
        password: "",
        confirmPassword: "",
    });

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
        if (dataPost.password === dataPost.confirmPassword) console.log(dataPost)
    };
    const handleOnchange = (event, field) => {
        setDataPost((prev) => {
            return {
                ...prev,
                [field]: event.target.value,
            };
        });
    };
    return (
        <div className="setting">
            <div className="setting__avatar">
                <p>Thay đổi avatar</p>
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
                        Chọn tệp
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
                <div>
                    {avatar && <img src={avatar.preview} alt="" width="10%" />}
                </div>
                <Button
                    sx={{ width: "100px", fontWeight: "600" }}
                    variant="outlined"
                >
                    Upload
                </Button>
            </div>
            <div className="setting__detail">
                <div className="setting__detail-more1">
                    <h3>Chi tiết tài khoản</h3>
                    <div className="language">
                        <p>Thay đổi ngôn ngữ: </p>
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
                <div className="setting__detail-more2">
                    <h3>Email</h3>
                    <p>Cập nhật email</p>
                    <TextField
                        label="Email mới"
                        variant="outlined"
                        value={dataPost.email}
                        onChange={(e) => handleOnchange(e, "email")}
                    />
                </div>
                <div className="setting__detail-more3">
                    <h3>Username</h3>
                    <p>Cập nhật username</p>
                    <TextField
                        label="Username mới"
                        variant="outlined"
                        value={dataPost.username}
                        onChange={(e) => handleOnchange(e, "username")}
                    />
                </div>
                <div className="setting__detail-more4">
                    <h3>Đổi mật khẩu</h3>
                    <p>Nhập mật khẩu mới</p>
                    <div className="test">
                        <TextField
                            label="Mật khẩu mới"
                            type="password"
                            variant="outlined"
                            value={dataPost.password}
                            onChange={(e) => handleOnchange(e, "password")}
                        />
                        <TextField
                            sx={{ marginLeft: "30px" }}
                            type="password"
                            label="Xác nhận mật khẩu mới"
                            variant="outlined"
                            value={dataPost.confirmPassword}
                            onChange={(e) =>
                                handleOnchange(e, "confirmPassword")
                            }
                        />
                    </div>
                </div>
                <div>
                    <Button
                        sx={{ marginTop: "30px" }}
                        variant="contained"
                        endIcon={<SendIcon />}
                        onClick={handleEventClick}
                    >
                        Lưu lại
                    </Button>
                </div>
                <div className="setting__detail-more5">
                    <Button
                        className="logout"
                        variant="contained"
                        onClick={() => {
                            navigate("/login");
                            // dispatch(loginFailure());
                        }}
                    >
                        Đăng xuất
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default Setting;