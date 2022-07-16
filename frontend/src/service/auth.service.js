import axiosClient from "utils/axiosClient";

const register = (userName, email, password) => {
  return axiosClient.post("/signup", {
    userName,
    email,
    password,
  });
};

const login = (userName, password) => {
  return axiosClient.post("/auth", {
    userName,
    password,
  });
};

const authService = {
  register,
  login,
};

export default authService;
