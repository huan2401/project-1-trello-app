import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import axiosClient from "utils/axiosClient";
import { HomeWrapper } from "./CustomStyled";

const Home = () => {
  useEffect(() => {
    axiosClient
      .post("/auth/login", {
        userName: "huan2401",
        password: "123456789",
      })
      .then((response) => console.log("home", response));
  }, []);
  return (
    <HomeWrapper>
      Home
      <br />
      <Link to={"/setting"}>Setting</Link>
      <br />
      <Link to={"/test"}>Test</Link>
      <br />
      <Link to={"/board"}>Board</Link>
    </HomeWrapper>
  );
};

export default Home;
