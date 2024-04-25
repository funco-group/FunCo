import { NavLink, useLocation } from "react-router-dom";

interface NavLinkProps {
  path: string;
  name: string;
}
function NavLinkComponent({ path, name }: NavLinkProps) {
  const location = useLocation();
  const nowTabName = location.pathname.split("/")[1];
  const isActive = nowTabName === path.split("/")[1];
  return (
    <NavLink to={path} className={isActive ? "nav-link active" : "nav-link"}>
      {name}
    </NavLink>
  );
}

export default NavLinkComponent;
