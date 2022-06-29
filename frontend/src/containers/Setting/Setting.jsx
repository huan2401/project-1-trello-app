import { useEffect, useState } from "react";
import React from "react";
import "./Setting.scss";
import Button from "@mui/material/Button";
import { Input, TextField } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import IconButton from "@mui/material/IconButton";
import PhotoCamera from "@mui/icons-material/PhotoCamera";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

const Setting = ({ username, email }) => {
    const [avatar, setAvatar] = useState();
    
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
                        id="outlined-basic"
                        label="Email mới"
                        variant="outlined"
                    />
                </div>
                <div className="setting__detail-more3">
                    <h3>Username</h3>
                    <p>Cập nhật username</p>
                    <TextField
                        id="outlined-basic"
                        label="Username mới"
                        variant="outlined"
                    />
                </div>
                <div className="setting__detail-more4">
                    <h3>Đổi mật khẩu</h3>
                    <p>Nhập mật khẩu mới</p>
                    <div className="test">
                        <TextField
                            id="outlined-basic1"
                            label="Mật khẩu mới"
                            variant="outlined"
                        />
                        <TextField
                            sx={{ marginLeft: "30px" }}
                            id="outlined-basic2"
                            label="Xác nhận mật khẩu mới"
                            variant="outlined"
                        />
                    </div>
                </div>
                <div>
                    <Button
                        sx={{ marginTop: "30px" }}
                        variant="contained"
                        endIcon={<SendIcon />}
                    >
                        Lưu lại
                    </Button>
                </div>
                <div className="setting__detail-more5">
                    <Button className="logout" variant="contained">
                        Đăng xuất
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default Setting;
