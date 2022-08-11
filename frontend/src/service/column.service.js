import axiosClient from "utils/axiosClient";

const findAllColumnByBoard = (board_id) => {
  return axiosClient.get("/column/find-all-by-board", { params: { board_id } });
};

const columnService = {
  findAllColumnByBoard,
};

export default columnService;
