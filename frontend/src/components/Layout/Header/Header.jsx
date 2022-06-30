import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { HeaderWrapper } from "./CustomStyled";

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
      <div
        className="logout"
        onClick={() => {
          navigate("/login");
        }}
      >
        Log out
      </div>
    </HeaderWrapper>
  );
};

export default Header;