import { Input, Modal } from "antd";
import React, { useState } from "react";
import styled from "styled-components";

const LabelList = styled.div`
  display: flex;
  gap: 5px;

  & > p:first-child {
    padding: 5px 10px;
    background-color: #f2d600;
    color: #fff;
    border-radius: 3px;
  }

  & > p:nth-child(2) {
    padding: 5px 10px;
    background-color: #eb5a46;
    color: #fff;
    border-radius: 3px;
  }

  & > p:last-child {
    padding: 5px 10px;
    background-color: #091e420a;
    color: #42526e;
    border-radius: 3px;
  }
`;

const ModalCard = ({ footer, isModalVisible, handleToggleShowModalCard }) => {
  console.log("isModalVisible", isModalVisible);
  const handleOk = () => {
    handleToggleShowModalCard(false);
  };

  const handleCancel = () => {
    handleToggleShowModalCard(false);
  };

  return (
    <Modal
      title="Project 1 : Trello App"
      visible={isModalVisible}
      onOk={handleOk}
      onCancel={handleCancel}
      footer={footer}
    >
      <div className="task-label" style={{ marginBottom: "20px" }}>
        <p style={{ marginBottom: "5px", fontWeight: "500" }}>Label</p>
        <LabelList>
          <p>Test</p>
          <p>Test</p>
          <p>+</p>
        </LabelList>
      </div>
      <div className="task-desc" style={{ marginBottom: "20px" }}>
        <p style={{ marginBottom: "5px", fontWeight: "500" }}>Description</p>
        <Input.TextArea placeholder={"add descriptions"} />
      </div>

      <div className="task-action">
        <p
          style={{
            marginBottom: "5px",
            fontWeight: "500"
          }}
        >
          Action
        </p>
        <div
          style={{
            display: "flex",
            gap: "10px",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <p
            style={{
              margin: 0,
              fontWeight: "500",
              width: "36px",
              height: "32px",
              borderRadius: "50%",
              backgroundColor: "orange",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            NH
          </p>
          <Input placeholder={"add comment"} />
        </div>
      </div>
    </Modal>
  );
};

export default ModalCard;
