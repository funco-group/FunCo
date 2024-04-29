'use client'

import { useEffect, useState } from 'react'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { useRouter } from 'next/navigation'
import {
  ProfileDiv,
  ProfileDropdownButton,
  ProfileDropdownContainer,
} from './ProfileDropdown.styled'

function ProfileDropdown({
  nickname,
  memberId,
  visible,
  setIsProfileOpen,
}: {
  nickname: string
  memberId: number
  visible: boolean
  setIsProfileOpen: React.Dispatch<React.SetStateAction<boolean>>
}) {
  const { logout } = useUserState()
  const router = useRouter()

  const handleNavigateMypage = () => {
    router.push(`member/${memberId}`)
    setIsProfileOpen((prev) => !prev)
  }

  const handleLogout = () => {
    logout()
    setIsProfileOpen((prev) => !prev)
    window.location.reload()
    window.location.href = '/'
  }

  const [open, setOpen] = useState(false)

  useEffect(() => {
    if (visible) {
      setOpen(true)
    } else {
      setTimeout(() => setOpen(false), 150)
    }
  }, [visible])

  if (!open) {
    return null
  }

  return (
    <ProfileDropdownContainer $visible={visible}>
      <ProfileDiv>
        <p>{nickname}</p>
      </ProfileDiv>
      <ProfileDropdownButton type="button" onClick={handleNavigateMypage}>
        마이페이지
      </ProfileDropdownButton>
      <ProfileDropdownButton type="button" onClick={handleLogout}>
        로그아웃
      </ProfileDropdownButton>
    </ProfileDropdownContainer>
  )
}

export default ProfileDropdown
