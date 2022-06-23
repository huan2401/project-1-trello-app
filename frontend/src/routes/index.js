import React, { lazy } from "react";
import { Navigate, useRoutes } from "react-router-dom";

// import Login from "containers/Login/Login";
// import Signup from "containers/Signup/Signup";
// import Home from "containers/Home/Home";
// import Setting from "containers/Setting/Setting";

const Home = lazy(() => import("containers/Home/Home.jsx"));
const Signup = lazy(() => import("containers/Signup/Signup.jsx"));
const Login = lazy(() => import("containers/Login/Login.jsx"));
const Setting = lazy(() => import("containers/Setting/Setting.jsx"));

/*
 * If route has children, it's a parent menu (not link to any pages)
 * You can change permissions to your IAM's permissions
 */
// export default function Router() {
//     return useRoutes([
//         {
//             path: "login",
//             element: <Login />,
//         },
//         {
//             path: "signup",
//             element: <Signup />,
//         },
//         {
//             path: "/",
//             element: <Home />,
//         },
//         {
//             path: "/setting",
//             element: <Setting />,
//         },
//     ]);
// };
export const AuthRoute = () => {
  return useRoutes([
    {
      path: "/",
      element: <Home />,
    },
    {
      path: "/setting",
      element: <Setting />,
    },
  ]);
};
export const PublicRoute = () => {
  return useRoutes([
    {
      path: "login",
      element: <Login />,
    },
    {
      path: "signup",
      element: <Signup />,
    },
  ]);
};
