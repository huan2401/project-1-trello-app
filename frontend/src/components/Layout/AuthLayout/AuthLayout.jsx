import React from "react";
import Header from "components/Layout/Header/Header";
import { Navigate, Outlet } from "react-router-dom";

const AuthLayout = ({ isAllowed, children }) => {

  if (!isAllowed) {
    return <Navigate to={"/login"} replace />;
  }

  console.log("child", isAllowed, children);

  return children ? (
    <>
      <Header />
      {children}
    </>
  ) : (
    <Outlet />
  );
};
export default AuthLayout;
