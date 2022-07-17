import React from "react";
import { useDispatch } from "react-redux";
import { Navigate, useNavigate } from "react-router-dom";
import { HeaderWrapper } from "./CustomStyled";
import { logout } from "slices/authSlice";
import { useSelector } from "react-redux";

const Header = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  return (
    <HeaderWrapper>
      <p>Header</p>
      <p
        onClick={() => {
          navigate("/");
        }}
      >
        Back to Home
      </p>
      <div className="logout" onClick={() => dispatch(logout())}>
        Log out
      </div>
    </HeaderWrapper>
  );
};

export default Header;
