
import React from 'react';
import { Link, Outlet } from 'react-router-dom';
import {HomeWrapper} from "./CustomStyled";

const Home = () => {

  return (
    <HomeWrapper>
      Home
      <Link to={'/setting'}>Setting</Link>
    </HomeWrapper>
  )
}

export default Home;
