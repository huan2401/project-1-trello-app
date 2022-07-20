import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { DragDropContext, Droppable } from "react-beautiful-dnd";
import Column from "./Column";

const column = {
  columnId: undefined,
  board: "",
  columnTitle: "",
  createdAt: "",
  updatedAt: "",
  status: "",
  position: undefined,
  deleted: false,
};

const task = {
  taskId: undefined,
  taskName: "",
  taskDescription: "",
  taskBackgroundUrl: "",
  createdAt: "",
  updatedAt: "",
  startAt: "",
  dueAt: "",
  isReviewed: false,
  isDone: false,
  deleted: false,
};

const initData = {
  tasks: {
    "task-1": { id: "task-1", content: "I am task 1" },
    "task-2": { id: "task-2", content: "I am task 2" },
    "task-3": { id: "task-3", content: "I am task 3" },
    "task-4": { id: "task-4", content: "I am task 4" },
  },
  columns: {
    "column-1": {
      id: "column-1",
      title: "Todo",
      taskIds: ["task-1", "task-2", "task-3", "task-4"],
    },
    "column-2": {
      id: "column-2",
      title: "In Progress",
      taskIds: [],
    },
  },
  columnOrder: ["column-1", "column-2"],
};

const Board = () => {
  const [data, setData] = useState(initData);
  const onDragEnd = (result) => {
    console.log("result change board", result);
  };
  return (
    <div>
      <DragDropContext onDragEnd={onDragEnd}>
        <Droppable
          droppableId="all-column"
          type="column"
          direction="horizontal"
        >
          {(provided, snapshot) => (
            <div
              isDraggingOver={snapshot.isDraggingOver}
              {...provided.droppableProps}
              ref={provided.innerRef}
              style={{ display: "flex", gap: "10px" }}
            >
              {data.columnOrder.map((columnId, index) => {
                const column = data.columns[columnId];
                const tasks = column.taskIds.map(
                  (taskId) => data.tasks[taskId]
                );
                return (
                  <Column
                    index={index}
                    key={column.id}
                    column={column}
                    tasks={tasks}
                  />
                );
              })}
              {provided.placeholder}
              <div>
                <button>Add column</button>
              </div>
            </div>
          )}
        </Droppable>
      </DragDropContext>
    </div>
  );
};
export default Board;
