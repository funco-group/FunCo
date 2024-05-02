'use client'

import FollowerContentTable from '@/components/TradeHistory/Follow/Follower/FollowerContentTable'
import FollowerTab from '@/components/TradeHistory/Follow/Follower/FollowerTab'
import { useState } from 'react'

function Follower() {
  const [nowTabName, setNowTabName] = useState<'all' | 'following' | 'settled'>(
    'all',
  )
  return (
    <div>
      <FollowerTab nowTabName={nowTabName} setNowTabName={setNowTabName} />
      <FollowerContentTable nowTabName={nowTabName} />
    </div>
  )
}

export default Follower
