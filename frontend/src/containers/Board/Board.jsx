import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { DragDropContext, Droppable } from "react-beautiful-dnd";
import Column from "./Column";
import { BoardWrapper } from "./CustomStyled";
import ModalCard from "components/common/Modal/ModalCard";

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

const mockDataContentTask = (
  <div>
    <p style={{ fontWeight: "500", fontSize: "18px" }}>
      Project 1 : Trello App
    </p>
    <p style={{ fontSize: "12px" }}>Create At : 11/08/2022</p>
  </div>
);

const initData = {
  tasks: {
    "task-1": {
      id: "task-1",
      content: mockDataContentTask,
    },
    "task-2": {
      id: "task-2",
      content: mockDataContentTask,
    },
    "task-3": {
      id: "task-3",
      content: mockDataContentTask,
    },
    "task-4": {
      id: "task-4",
      content: mockDataContentTask,
    },
    "task-5": {
      id: "task-5",
      content: mockDataContentTask,
    },
    "task-6": {
      id: "task-6",
      content: mockDataContentTask,
    },
    "task-7": {
      id: "task-7",
      content: mockDataContentTask,
    },
    "task-8": {
      id: "task-8",
      content: mockDataContentTask,
    },
    "task-9": {
      id: "task-9",
      content: mockDataContentTask,
    },
    "task-10": {
      id: "task-10",
      content: mockDataContentTask,
    },
    "task-11": {
      id: "task-11",
      content: mockDataContentTask,
    },
    "task-12": {
      id: "task-12",
      content: mockDataContentTask,
    },
  },
  columns: {
    "column-1": {
      id: "column-1",
      title: "Todo",
      taskIds: ["task-1", "task-2", "task-3", "task-4"],
    },
    "column-2": {
      id: "column-2",
      title: "Doing",
      taskIds: ["task-5", "task-6", "task-7", "task-8", "task-9"],
    },
    "column-3": {
      id: "column-3",
      title: "Test",
      taskIds: ["task-10", "task-11", "task-12"],
    },
    "column-4": {
      id: "column-4",
      title: "Done",
      taskIds: [],
    },
  },
  columnOrder: ["column-1", "column-2", "column-3", "column-4"],
};

const Board = () => {
  const [data, setData] = useState(initData);
  const onDragEnd = (result) => {
    console.log("result change board", result);
  };
  return (
    <BoardWrapper>
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
              <div className="column-add">
                <p>+ Add another list</p>
              </div>
            </div>
          )}
        </Droppable>
      </DragDropContext>
    </BoardWrapper>
  );
};
export default Board;
