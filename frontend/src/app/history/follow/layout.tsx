import FollowTab from '@/components/TradeHistory/Follow/FollowTab'

function FollowLayout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <FollowTab />
      {children}
    </div>
  )
}

export default FollowLayout
