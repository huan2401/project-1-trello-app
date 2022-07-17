import axiosClient from "utils/axiosClient";

const register = (userName, password, email, firstName, lastName, sex) => {
  return axiosClient.post("/user/signup", {
    userName,
    password,
    email,
    firstName,
    lastName,
    sex
  });
};

const login = (userName, password) => {
  return axiosClient.post("/auth/login", {
    userName,
    password,
  });
};

const authService = {
  register,
  login,
};

export default authService;
