import axiosClient from "utils/axiosClient";

const findAllBoard = () => {
  return axiosClient.get("/board/find-all");
};

const boardService = {
  findAllBoard,
};

export default boardService;
