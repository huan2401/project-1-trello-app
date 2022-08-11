import axiosClient from "utils/axiosClient";

const findAllTaskByColumn = (column_id) => {
  return axiosClient.get("/column_task/find-all-by-column", { params: { column_id } });
};

const findByTaskId = (task_id) => {
  return axiosClient.get("/task/find-by-task-id", { params: { task_id } });
};

const taskService = {
  findAllTaskByColumn,
  findByTaskId
};

export default taskService;
