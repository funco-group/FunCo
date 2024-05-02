'use client'

import { useEffect, useRef, useState } from 'react'
import Link from 'next/link'
import NavLinkComponent from '@/components/Header/NavLinkComponent'
import useUserState from '@/hooks/recoilHooks/useUserState'
import useSSE from '@/hooks/useSSE'
import useCloseDropdown from '@/hooks/useCloseDropdown'
import AssetHistoryNav from '@/components/Header/AssetHistoryNav'
import NotiDropdown from '@/components/Header/NotiDropdown'
import ProfileDropdown from '@/components/Header/ProfileDropdown'
import LogoSVG from '@/../public/icon/logo.svg'
import GoogleSVG from '@/../public/icon/google.svg'
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
} from './styled'

function Navbar() {
  const { user, unReadCount } = useUserState()
  const notiDropDownRef = useRef(null)
  const profileDropdownRef = useRef(null)
  const [isNotiOpen, setIsNotiOpen] = useCloseDropdown(notiDropDownRef, false)
  const [isProfileOpen, setIsProfileOpen] = useCloseDropdown(
    profileDropdownRef,
    false,
  )
  const [mounted, setMounted] = useState(false)

  useEffect(() => {
    setMounted(true)
  }, [])

  const handleLoginClick = () => {
    window.location.href =
      'https://accounts.google.com/o/oauth2/auth?' +
      `client_id=${process.env.NEXT_PUBLIC_CLIENT_ID}&` +
      `redirect_uri=${process.env.NEXT_PUBLIC_REDIRECT_URL}&` +
      'response_type=code&' +
      'scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile'
  }

  const handleNotiDropdown = () => {
    setIsNotiOpen((prev) => !prev)
  }
  const handleProfileDropdown = () => {
    setIsProfileOpen((prev) => !prev)
  }

  useSSE()

  return (
    mounted && (
      <NavBarContainer>
        <NavBarLeftDiv>
          <Link href="/">
            <LogoSVG />
          </Link>
          <NavBarLeftLinkDiv>
            <NavLinkComponent path="/trade/KRW-BTC" name="거래소" />
            <AssetHistoryNav path="/history/asset" name="투자내역" />
            <NavLinkComponent path="/rank" name="랭킹" />
          </NavBarLeftLinkDiv>
        </NavBarLeftDiv>
        {user ? (
          <NavBarRightDiv>
            <div ref={notiDropDownRef}>
              <NavBarNotiProfileDiv onClick={handleNotiDropdown}>
                <NavBarNotiImg
                  src="/icon/notification-off.svg"
                  alt="noti-off-icon"
                  draggable={false}
                />
                {unReadCount && unReadCount > 0 ? (
                  <NavBarNotiPointDiv>
                    <p>{unReadCount}</p>
                  </NavBarNotiPointDiv>
                ) : null}
              </NavBarNotiProfileDiv>
              <NotiDropdown visible={isNotiOpen} />
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
                    src="icon/user-default.svg"
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
            <GoogleSVG />
            <p>Google로 시작하기</p>
          </NavBarLoginButton>
        )}
      </NavBarContainer>
    )
  )
}

export default Navbar
