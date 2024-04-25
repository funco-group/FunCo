import { useLocation, useNavigate } from "react-router-dom";
import { NavComponent } from "./Navbar.styled";
import useLoginAlertModalState from "@/hooks/recoilHooks/useLoginAlertModalState";
import userState from "@/recoils/user";
import { useRecoilValue } from "recoil";

interface NavLinkProps {
  path: string;
  name: string;
}

function AssetHistoryNav({ path, name }: NavLinkProps) {
  const location = useLocation();
  const navigate = useNavigate();
  const nowTabName = location.pathname.split("/")[1];
  const isActive = nowTabName === path.split("/")[1];
  const { onLoginAlertModal } = useLoginAlertModalState();
  const user = useRecoilValue(userState);

  const clickNav = () => {
    if (user.user) {
      navigate(path);
    } else {
      onLoginAlertModal();
    }
  };

  return (
    <NavComponent onClick={clickNav} $active={isActive ? true : false}>
      {name}
    </NavComponent>
  );
}

export default AssetHistoryNav;
