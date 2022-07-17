import React, { useState, useEffect } from "react";
import "./App.less";
// import { useSelector } from "react-redux";
import { useSelector, useDispatch } from "react-redux";
import { checkLogin } from "slices/authSlice";
import AuthLayout from "components/Layout/AuthLayout/AuthLayout";
import { Routes, Route, Navigate } from "react-router-dom";
import Home from "containers/Home/Home";
import Setting from "containers/Setting/Setting";
import Login from "containers/Login/Login";
import Signup from "containers/Signup/Signup";
import NotFound from "containers/NotFound/NotFound";
import Board from "containers/Board/Board";
import Test from "containers/Test/Test";

function App() {
  const dispatch = useDispatch();
  const isLogin = useSelector((state) => state.auth.login);
  console.log("isLogin", isLogin);
  useEffect(() => {
    if (localStorage.getItem("token")) {
      dispatch(checkLogin(true));
    }
  }, [isLogin]);
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="signup" element={<Signup />} />

      <Route
        path="board"
        element={
          <AuthLayout isAllowed={isLogin}>
            <Board />
          </AuthLayout>
        }
      >
        <Route path=":id" element={<Board />} />
      </Route>
      <Route
        path="test"
        element={
          <AuthLayout isAllowed={isLogin}>
            <Test />
          </AuthLayout>
        }
      />
      <Route
        path="setting"
        element={
          <AuthLayout isAllowed={isLogin}>
            <Setting />
          </AuthLayout>
        }
      />
      <Route
        path="/"
        element={
          <AuthLayout isAllowed={isLogin}>
            <Home />
          </AuthLayout>
        }
      />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

export default App;
