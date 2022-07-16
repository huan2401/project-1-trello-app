import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import axiosClient from "utils/axiosClient";
import { HomeWrapper } from "./CustomStyled";

const Home = () => {
  useEffect(() => {
    axiosClient
      .post("/auth", {
        userName: "huan2401",
        password: "123456789",
      })
      .then((response) => console.log("home", response));
  }, []);
  return (
    <HomeWrapper>
      Home
      <Link to={"/setting"}>Setting</Link>
    </HomeWrapper>
  );
};

export default Home;
