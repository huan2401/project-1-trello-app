import React from "react";
// import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import "./Header.scss";
import Avatar from "@mui/material/Avatar";
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

const Header = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  return (
    <div className="header1">
      <FontAwesomeIcon icon={faBars} className="header_apps" />
      <FontAwesomeIcon
        icon={faHouse}
        className="header_home"
        onClick={() => {
          navigate("/");
        }}
      />

      <div className="button-border">
        <button className="button">New</button>
      </div>

      <div className="header_search">
        <input type="text" placeholder="Searching..." />
        <button className="search-btn">
          <FontAwesomeIcon
            icon={faMagnifyingGlass}
            className="header_search-icon"
          />
        </button>
      </div>

      <Avatar
        className="avatar1"
        alt="Remy Sharp"
        src="/static/images/avatar/1.jpg"
        sx={{ width: 40, height: 40, left: 700 }}
      />

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
  );
};

export default Header;
