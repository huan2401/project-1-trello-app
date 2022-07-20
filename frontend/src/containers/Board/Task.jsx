import React from "react";
import styled from "@emotion/styled";
import { Draggable } from "react-beautiful-dnd";

const Container = styled.div`
  background-color: #fff;
  border-radius: 3px;
  padding: 6px 8px 2px;
`;

const Task = ({ task, index }) => {
  return (
    <Draggable draggableId={task.id} index={index} type="task">
      {(provided, snapshot) => (
        <Container
          ref={provided.innerRef}
          {...provided.dragHandleProps}
          {...provided.draggableProps}
          isDragging={snapshot.isDragging}
        >
          {task.content}
        </Container>
      )}
    </Draggable>
  );
};

export default Task;
