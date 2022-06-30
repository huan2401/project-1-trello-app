import React, { lazy } from "react";
import { Navigate, useRoutes } from "react-router-dom";

const Home = lazy(() => import("containers/Home/Home.jsx"));
const Signup = lazy(() => import("containers/Signup/Signup.jsx"));
const Login = lazy(() => import("containers/Login/Login.jsx"));
const Setting = lazy(() => import("containers/Setting/Setting.jsx"));

// export const AuthRoute = () => {
//   return useRoutes([
//     {
//       path: "/",
//       element: <Home />,
//     },
//     {
//       path: "/setting",
//       element: <Setting />,
//     },
//   ]);
// };
// export const PublicRoute = () => {
//   return useRoutes([
//     {
//       path: "login",
//       element: <Login />,
//     },
//     {
//       path: "signup",
//       element: <Signup />,
//     },
//   ]);
// };
