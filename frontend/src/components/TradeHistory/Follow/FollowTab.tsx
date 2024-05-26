'use client'

import { useEffect, useState } from 'react'
import { usePathname, useRouter } from 'next/navigation'
import TabButton from '@/components/Common/Button/TabButton.styled'
import FollowTabContainer from './FollowTab.styled'

function FollowTab() {
  const router = useRouter()
  const pathname = usePathname()
  const tabNameList = [
    ['팔로잉', 'following'],
    ['팔로워', 'follower'],
  ]

  const [nowTabName, setNowTabName] = useState<string | null>(
    pathname.split('/')[3],
  )

  useEffect(() => {
    if (nowTabName !== pathname.split('/')[3]) {
      setNowTabName(pathname.split('/')[3])
    }
  }, [pathname, nowTabName])

  const handleTabClick = (selectedTabName: string) => {
    router.push(`/history/follow/${selectedTabName}`)
    setNowTabName(selectedTabName)
  }

  return (
    <FollowTabContainer>
      {tabNameList.map((tabName) => (
        <TabButton
          key={tabName[1]}
          width="6.25rem"
          height="2.5rem"
          $active={tabName[1] === nowTabName}
          onClick={() => handleTabClick(tabName[1])}
          radius={tabName[1] === 'following' ? 'left' : 'right'}
        >
          {tabName[0]}
        </TabButton>
      ))}
    </FollowTabContainer>
  )
}

export default FollowTab
