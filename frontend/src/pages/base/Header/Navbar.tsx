import { NavLink } from "react-router-dom";
import { useRef, useState } from "react";
import {
  NavBarContainer,
  NavBarLeftDiv,
  NavBarLeftLinkDiv,
  NavBarLoginButton,
  NavBarNotiImg,
  NavBarNotiPointDiv,
  NavBarNotiProfileDiv,
  NavBarProfileImg,
  NavBarRightDiv,
} from "./Navbar.styled";
import logo from "@/assets/icon/logo.svg";
import googleLogo from "@/assets/icon/google.svg";
import notificationOff from "@/assets/icon/notification-off.svg";
import defaultImage from "@/assets/icon/user-default.svg";
import NavLinkComponent from "./NavLinkComponent";
import useUserState from "@/hooks/recoilHooks/useUserState";
import NotiDropdown from "./NotiDropdown";
import ProfileDropdown from "./ProfileDropdown";
import useCloseDropdown from "@/hooks/useCloseDropdown";
import useSSE from "@/hooks/useSSE";
import AssetHistoryNav from "./AssetHistoryNav";

function Navbar() {
  const { user } = useUserState();
  const notiDropDownRef = useRef(null);
  const profileDropdownRef = useRef(null);
  const [isNotiOpen, setIsNotiOpen] = useCloseDropdown(notiDropDownRef, false);
  const [isProfileOpen, setIsProfileOpen] = useCloseDropdown(
    profileDropdownRef,
    false,
  );

  const [unReadCount, setUnReadCount] = useState(user?.unReadCount);

  const handleLoginClick = () => {
    window.location.href =
      "https://accounts.google.com/o/oauth2/auth?" +
      `client_id=${import.meta.env.VITE_CLIENT_ID}&` +
      `redirect_uri=${import.meta.env.VITE_REDIRECT_URL}&` +
      "response_type=code&" +
      "scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
  };

  const handleNotiDropdown = () => {
    setIsNotiOpen((prev) => !prev);
  };
  const handleProfileDropdown = () => {
    setIsProfileOpen((prev) => !prev);
  };

  useSSE(setUnReadCount);

  return (
    <NavBarContainer>
      <NavBarLeftDiv>
        <NavLink to="/">
          <img src={logo} alt="main-logo" draggable={false} />
        </NavLink>
        <NavBarLeftLinkDiv>
          <NavLinkComponent path="/trade/KRW-BTC" name="거래소" />
          {/* <NavLinkComponent path="/history/asset" name="투자내역" /> */}
          <AssetHistoryNav path="/history/asset" name="투자내역" />
          <NavLinkComponent path="/rank" name="랭킹" />
        </NavBarLeftLinkDiv>
      </NavBarLeftDiv>
      {user ? (
        <NavBarRightDiv>
          <div ref={notiDropDownRef}>
            <NavBarNotiProfileDiv onClick={handleNotiDropdown}>
              <NavBarNotiImg
                src={notificationOff}
                alt="noti-off-icon"
                draggable={false}
              />
              {unReadCount && unReadCount > 0 ? (
                <NavBarNotiPointDiv>
                  <p>{unReadCount}</p>
                </NavBarNotiPointDiv>
              ) : null}
            </NavBarNotiProfileDiv>
            <NotiDropdown
              visible={isNotiOpen}
              setUnReadCount={setUnReadCount}
            />
          </div>
          <div ref={profileDropdownRef}>
            <NavBarNotiProfileDiv onClick={handleProfileDropdown}>
              {user.profileUrl ? (
                <NavBarProfileImg
                  src={user.profileUrl}
                  alt="user-profile"
                  draggable={false}
                />
              ) : (
                <NavBarProfileImg
                  src={defaultImage}
                  alt="default-profile"
                  draggable={false}
                />
              )}
            </NavBarNotiProfileDiv>
            <ProfileDropdown
              nickname={user.nickname}
              memberId={user.memberId}
              visible={isProfileOpen}
              setIsProfileOpen={setIsProfileOpen}
            />
          </div>
        </NavBarRightDiv>
      ) : (
        <NavBarLoginButton type="button" onClick={handleLoginClick}>
          <img src={googleLogo} alt="google-logo" draggable={false} />
          <p>Google로 시작하기</p>
        </NavBarLoginButton>
      )}
    </NavBarContainer>
  );
}

export default Navbar;
