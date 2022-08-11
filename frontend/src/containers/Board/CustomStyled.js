import styled from "styled-components";

export const BoardWrapper = styled.div`
  padding: 5px;
  height: calc(100vh - 60px);
  background-image: url("http://nhandaovadoisong.com.vn/wp-content/uploads/2019/05/anh-thien-nhien-dep.jpg");
  background-size: cover;
  p {
    margin: 0;
  }
  .column-add {
    & > p {
      cursor: pointer;
      padding: 5px 10px;
      border-radius: 5px;
      background-color: #ebecf0;
    }
  }
  .column-title {
    margin-bottom: 20px;
  }
  .task-add {
    cursor: pointer;
  }
`;
