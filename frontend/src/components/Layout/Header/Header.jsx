import React from "react";
// import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import "./Header.scss";
// import Avatar from "@mui/material/Avatar";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBars,
  faGear,
  faHouse,
  faMagnifyingGlass,
  faRightFromBracket,
} from "@fortawesome/free-solid-svg-icons";
import { logout } from "slices/authSlice";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { Avatar } from "antd";

const Header = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.auth.user);
  console.log("header user", user);
  const navigate = useNavigate();
  return (
    <div className="header">
      <div>
        <div className="header-left">
          {/* <FontAwesomeIcon icon={faBars} className="header_apps" /> */}
          <FontAwesomeIcon
            icon={faHouse}
            className="header_home"
            onClick={() => {
              navigate("/");
            }}
          />

          {/* <div className="button-border">
        <button className="button">New</button>
      </div> */}

          <div className="header_search">
            <input type="text" placeholder="Searching..." />
            <button className="search-btn">
              <FontAwesomeIcon
                icon={faMagnifyingGlass}
                className="header_search-icon"
              />
            </button>
          </div>
        </div>

        <div className="header-right">
          <div className="header-user">
            <Avatar src="https://joeschmoe.io/api/v1/random" />
            <p>{user && user.userName}</p>
          </div>
          <FontAwesomeIcon
            icon={faGear}
            // className="header_setting-icon"
            className="header_setting"
            onClick={() => {
              navigate("/setting");
            }}
          />

          <FontAwesomeIcon
            icon={faRightFromBracket}
            // className="header_logout-icon"
            className="header_logout"
            onClick={() => dispatch(logout())}
          />
        </div>
      </div>
    </div>
  );
};

export default Header;
