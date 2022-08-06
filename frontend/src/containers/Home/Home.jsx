import React, { useEffect } from "react";
import "./Home.scss";

import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChessBoard,
  faClapperboard,
  faGear,
  faUsers,
  faClock,
  faHouseLaptop,
  faNetworkWired,
} from "@fortawesome/free-solid-svg-icons";
import { findWorkSpaceByUser } from "slices/workSpaceSlice";
import { findAllBoard } from "slices/boardSlice";
import { findAllColumnByBoard } from "slices/columnSlice";
import { findAllTaskByColumn, findByTaskId } from "slices/taskSlice";
import { useDispatch, useSelector } from "react-redux";

const Home = () => {
  const dispatch = useDispatch();
  const store = useSelector((state) => state);
  const board = useSelector((state) => state.board.data);
  useEffect(() => {
    dispatch(findWorkSpaceByUser());
    dispatch(findAllBoard());
    dispatch(findAllColumnByBoard(2));
    dispatch(findAllTaskByColumn(4));
    dispatch(findByTaskId(4));
  }, []);
  console.log("store redux", store);
  return (
    <div className="home">
      <div className="home-sidebar">
        <ul className="list-sidebar">
          <li>
            <a href="">
              <span>
                <FontAwesomeIcon icon={faChessBoard} />
              </span>
              <h3>Board</h3>
            </a>
          </li>
          <li>
            <a href="">
              <span>
                <FontAwesomeIcon icon={faClapperboard} />
              </span>
              <h3>Sample</h3>
            </a>
          </li>
          <li>
            <a href="">
              <span>
                <FontAwesomeIcon icon={faUsers} />
              </span>
              <h3>Member</h3>
            </a>
          </li>
          <li>
            <a href="">
              <span>
                <FontAwesomeIcon icon={faGear} />
              </span>
              <h3>Setting</h3>
            </a>
          </li>
          {/* <li>
                        <a href="">
                            <span>
                                <FontAwesomeIcon icon={faChessBoard} />
                            </span>
                            <h3>Setting</h3>
                        </a>
                    </li> */}
        </ul>
      </div>
      <div className="home-content">
        <div className="recent">
          <p className="title">
            <span>
              <FontAwesomeIcon icon={faClock} />
            </span>
            <h3
              onClick={() => {
                dispatch(findWorkSpaceByUser());
              }}
            >
              Recently Viewed
            </h3>
          </p>
          <div className="board_list">
            <a href="">
              <h3>First Board</h3>
              <img
                src="http://chiase24.com/wp-content/uploads/2022/02/tang-hap-hanh-na_n-blue-wallpaper-aap-nhayt-1.jpg"
                alt=""
              />
            </a>
            <a href="">
              <h3>Second Board</h3>
              <img
                src="https://truongquochoc.com/wp-content/uploads/2020/10/25-hinh-nen-iphone-6-va-6s-hd-dep-nhat-the-gioi-1.jpg"
                alt=""
              />
            </a>
            <a href="">
              <h3>Third Board</h3>
              <img
                src="https://i.pinimg.com/736x/fd/b4/da/fdb4da07ecd3be76083fe7c13bdb81e1.jpg"
                alt=""
              />
            </a>
          </div>
        </div>
        <div className="workspace">
          <p className="title">
            <span>
              <FontAwesomeIcon icon={faHouseLaptop} />
            </span>
            <h3>Your Workspace</h3>
          </p>
          <div className="workspace_list">
            <a href="">
              <h3>First Workspace</h3>
              <img
                src="http://chiase24.com/wp-content/uploads/2022/02/tang-hap-hanh-na_n-blue-wallpaper-aap-nhayt-1.jpg"
                alt=""
              />
            </a>
            <a href="">
              <h3>Second Workspace</h3>
              <img
                src="https://truongquochoc.com/wp-content/uploads/2020/10/25-hinh-nen-iphone-6-va-6s-hd-dep-nhat-the-gioi-1.jpg"
                alt=""
              />
            </a>
            <a href="">
              <h3>Third Workspace</h3>
              <img
                src="https://upanh123.com/wp-content/uploads/2021/03/hinh-nen-mau-hong10.jpg"
                alt=""
              />
            </a>
          </div>
        </div>
        <div className="workspace_other">
          <p className="title">
            <span>
              <FontAwesomeIcon icon={faNetworkWired} />
            </span>
            <h3>Your Friend's Workspace</h3>
          </p>
          <div className="workspace_list">
            <a href="">
              <h3 className="name">Chien Dao</h3>
              <h3>First Workspace</h3>
              <img
                src="http://chiase24.com/wp-content/uploads/2022/02/tang-hap-hanh-na_n-blue-wallpaper-aap-nhayt-1.jpg"
                alt=""
              />
            </a>
            <a href="">
              <h3 className="name">Van Huan</h3>
              <h3>Second Workspace</h3>
              <img
                src="https://truongquochoc.com/wp-content/uploads/2020/10/25-hinh-nen-iphone-6-va-6s-hd-dep-nhat-the-gioi-1.jpg"
                alt=""
              />
            </a>
            <a href="">
              <h3 className="name">Duc Toan</h3>
              <h3>Third Workspace</h3>
              <img
                src="https://upanh123.com/wp-content/uploads/2021/03/hinh-nen-mau-hong10.jpg"
                alt=""
              />
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
