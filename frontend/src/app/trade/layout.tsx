import WideLayout from '@/components/layout/WideLayout'
import React from 'react'

function TradeLayout({ children }: { children: React.ReactNode }) {
  return (
    <WideLayout>
      <div>chart</div>
      {children}
      <div>price</div>
    </WideLayout>
  )
}

export default TradeLayout
