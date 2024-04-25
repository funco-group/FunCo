import { Outlet } from "react-router-dom";
import FollowTab from "./FollowTab";

function index() {
  return (
    <div>
      <FollowTab />
      <Outlet />
    </div>
  );
}

export default index;
