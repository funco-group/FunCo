'use client'

import { useEffect, useState } from 'react'
import { TabContainer, TabItemDiv } from '@/components/Crypto/Tab.styled'
import { usePathname, useRouter } from 'next/navigation'

function HistoryTab() {
  const router = useRouter()
  const pathname = usePathname()
  const tabList = [
    ['보유자산', 'asset'],
    ['투자손익', 'result'],
    ['자산변동내역', 'change'],
    ['팔로우', 'follow'],
    ['미체결', 'orders'],
  ]

  const [nowTabName, setNowTabName] = useState<string | null>(
    pathname.split('/')[2],
  )

  useEffect(() => {
    if (nowTabName !== pathname.split('/')[2]) {
      setNowTabName(pathname.split('/')[2])
    }
  }, [pathname, nowTabName])

  const handleTabClick = (selectedTabName: string) => {
    router.push(`/history/${selectedTabName}`)
    setNowTabName(selectedTabName)
  }

  return (
    <TabContainer $columns={5}>
      {tabList.map((tab) => (
        <TabItemDiv
          key={tab[1]}
          $active={tab[1] === nowTabName}
          onClick={() => handleTabClick(tab[1])}
        >
          {tab[0]}
        </TabItemDiv>
      ))}
    </TabContainer>
  )
}

export default HistoryTab
