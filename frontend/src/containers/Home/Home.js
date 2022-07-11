import React, { useEffect } from "react";
import "./Home.scss";

import { Link, useNavigate } from "react-router-dom";

const Home = () => {
    return (
        <div className="home">
            <div className="recent">
                <p className="title">Đã xem gần đây</p>
                <div className="board_list">
                    <a href="">
                        <h3>First Board</h3>
                        <img
                            src="http://chiase24.com/wp-content/uploads/2022/02/tang-hap-hanh-na_n-blue-wallpaper-aap-nhayt-1.jpg"
                            alt=""
                        />
                    </a>
                    <a href="">
                        <h3>Second Board</h3>
                        <img
                            src="https://truongquochoc.com/wp-content/uploads/2020/10/25-hinh-nen-iphone-6-va-6s-hd-dep-nhat-the-gioi-1.jpg"
                            alt=""
                        />
                    </a>
                    <a href="">Board 3</a>
                </div>
            </div>
            <div className="workspace">
                <p className="title">Các không gian làm việc của bạn</p>
                <div className="workspace_list">
                    <a href="">Workspace 1</a>
                    <a href="">Workspace 2</a>
                    <a href="">Workspace 3</a>
                </div>
            </div>
        </div>
    );
};

export default Home;
