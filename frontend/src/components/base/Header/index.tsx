import { Outlet } from "react-router-dom";

export default function index() {
  return (
    <div>
      Header
      <Outlet />
    </div>
  );
}
