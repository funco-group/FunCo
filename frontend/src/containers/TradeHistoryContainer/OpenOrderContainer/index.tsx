// import { getAllOpenTradeList } from '@/apis/trade'
import OpenOrderContentTable from '@/components/TradeHistory/OpenOrder/OpenOrderContentTable'
// import { TradeListType } from '@/interfaces/TradeType'

// async function getData() {
//   const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/v1/trade/open-orders?pages=0&size=100`, {
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//       Authorization: `Bearer ${userInfo.user.accessToken}`
//     },
//       cache: 'no-cache'
//     })
// }

// server component에서 server action으로 데이터를 땡겨올까? 했는데
// 우리 access token을 client에서만 보관하고 있어서 불가능...
// 검색해봐서 나온 해결책 1. cookie 나머지는 아직 모르겠음...

async function OpenOrder() {
  // const data: TradeListType[] = await getData()
  return (
    <div>
      <OpenOrderContentTable />
    </div>
  )
}

export default OpenOrder
