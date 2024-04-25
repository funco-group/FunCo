import { Outlet, useLocation } from "react-router-dom";
import Navbar from "./Navbar";
import BodyContainer from "./styled";

function Index() {
  const location = useLocation();

  return (
    <div>
      <Navbar />
      <BodyContainer $main={location.pathname === "/"}>
        <Outlet />
      </BodyContainer>
    </div>
  );
}

export default Index;
