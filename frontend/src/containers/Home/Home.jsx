import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { HomeWrapper } from "./CustomStyled";

const Home = () => {
  return (
    <HomeWrapper>
      Home
      <Link to={"/setting"}>Setting</Link>
    </HomeWrapper>
  );
};

export default Home;
