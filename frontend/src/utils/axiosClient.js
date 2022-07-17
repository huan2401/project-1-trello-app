import axios from "axios";
const axiosClient = axios.create({
  baseURL: "https://project1testingserver.herokuapp.com/project1/api",
  headers: {
    // 'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});
// Add a request interceptor
axiosClient.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
axiosClient.interceptors.response.use(
  function (response) {
    if(response.data.accessToken){
      localStorage.setItem("token",response.data.accessToken)
      localStorage.setItem("login",true)
    }
    return response;
  },
  function (error) {
    // Do something with response error
    return Promise.reject(error);
  }
);

export default axiosClient;
