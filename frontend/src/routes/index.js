import { lazy } from 'react';

const Home = lazy(() => import('containers/Home'));


/*
 * If route has children, it's a parent menu (not link to any pages)
 * You can change permissions to your IAM's permissions
 */
const routes= [
  {
    path: '/',
    title: 'Home',
    component: Home,
  },
];

export default routes;