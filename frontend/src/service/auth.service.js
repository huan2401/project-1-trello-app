import axiosClient from "utils/axiosClient";

const register = (userName, password, email, firstName, lastName, sex) => {
  return axiosClient.post("/auth/signup", {
    userName,
    password,
    email,
    firstName,
    lastName,
    sex,
  });
};

const login = (userName, password) => {
  return axiosClient.post("/auth/login", {
    userName,
    password,
  });
};

const updateProfile = (email, firstName, lastName, sex,phoneNumber) => {
  return axiosClient.put("/user/update", {
    email,
    firstName,
    lastName,
    sex,
    phoneNumber
  });
};

const changePassword = (passWord, previousPassword) => {
  return axiosClient.put("/user/change-password", {
    passWord,
    previousPassword,
  });
};

const authService = {
  register,
  login,
  updateProfile,
  changePassword
};

export default authService;
