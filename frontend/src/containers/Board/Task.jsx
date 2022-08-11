import React, { useState } from "react";
import styled from "@emotion/styled";
import { Draggable } from "react-beautiful-dnd";
import ModalCard from "components/common/Modal/ModalCard";

const Container = styled.div`
  background-color: #fff;
  border-radius: 3px;
  padding: 6px 8px 2px;
`;

const Task = ({ task, index }) => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const handleToggleShowModalCard = (value) => {
    setIsModalVisible(value);
  };
  return (
    <Draggable draggableId={task.id} index={index} type="task">
      {(provided, snapshot) => (
        <>
          <Container
            ref={provided.innerRef}
            {...provided.dragHandleProps}
            {...provided.draggableProps}
            isDragging={snapshot.isDragging}
            onClick={() => {
              setIsModalVisible(true);
            }}
          >
            {task.content}
          </Container>
          <ModalCard
            footer={[]}
            isModalVisible={isModalVisible}
            handleToggleShowModalCard={handleToggleShowModalCard}
          />
        </>
      )}
    </Draggable>
  );
};

export default Task;
