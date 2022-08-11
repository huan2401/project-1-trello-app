import axiosClient from "utils/axiosClient";

const findWorkSpaceByUser = () => {
  return axiosClient.get("/user-workspace/find-by-user");
};

const workSpaceService = {
  findWorkSpaceByUser,
};

export default workSpaceService;
