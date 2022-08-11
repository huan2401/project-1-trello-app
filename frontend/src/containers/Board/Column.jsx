import React from "react";
import styled from "@emotion/styled";
import { Droppable, Draggable } from "react-beautiful-dnd";
import Task from "./Task";

const Container = styled.div`
  width: 272px;
  background-color: #ebecf0;
  border-radius: 3px;
  padding: 10px;
  height: fit-content;
`;

const ColumnContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const Column = ({ tasks, column, index }) => {
  return (
    <Draggable draggableId={column.id} index={index} type="column">
      {(provided) => (
        <Container
          ref={provided.innerRef}
          {...provided.draggableProps}
          {...provided.dragHandleProps}
        >
          <p className="column-title">{column.title}</p>
          <Droppable droppableId={column.id} type="task">
            {(provided, snapshot) => (
              <ColumnContent
                isDraggingOver={snapshot.isDraggingOver}
                ref={provided.innerRef}
                {...provided.droppableProps}
              >
                {tasks.map((task, index) => (
                  <Task key={task.id} task={task} index={index} />
                ))}
                <div className="task-add">
                  <p>+ Add card</p>
                </div>
                {provided.placeholder}
              </ColumnContent>
            )}
          </Droppable>
        </Container>
      )}
    </Draggable>
  );
};

export default Column;
