export interface CoinVolumeType {
  volume: number
}

export interface AssetCoinType {
  ticker: string
  volume: number
  averagePrice: number
}

export interface AssetHistoryType {
  date: string
  name: string
  assetType: string
  tradeType: string
  volume: number
  price: number | null
  orderCash: number
  commission: number | null
  settlement: number | null
}

export interface AssetFuturesType {
  ticker: string
  tradeType: string
  orderCash: number
  price: number
  leverage: number
}

export interface AssetResponseType {
  memberId: number
  cash: number
  followingInvestment: number
  holdingCoinInfos: AssetCoinType[]
  activeFutureInfos: AssetFuturesType[]
}

export interface AssetType {
  imgSrc: string
  type: string
  name: string
  volume: number | null
  averagePrice: number | null
  price: number | string | null
  evaluationAmount: number | null
  evaluationProfit: number | null
}

export interface CoinCurPrice {
  ticker: string
  price: number
}

export interface TotalAssetType {
  cash: number
  price: number
  evaluationAmount: number
  asset: number
  returnResult: number
  evaluationProfit: number
}

export interface OptionType {
  value: string
  name: string
}

export interface CoinAssetType {
  date: string
  ticker: string
  tradeType: string
  volume: number
  price: number
  orderCash: number
  endingCash: number
}

export interface FuturesAssetType {
  date: string
  ticker: string
  tradeType: string
  price: number
  orderCash: number
  endingCash: number
}

export interface FollowAssetType {
  date: string
  tradeType: string
  investment: number
  settlement: number
  returnRate: number
  commission: number
  settleDate: string
}

export interface PortfolioAssetType {
  date: string
  portfolioName: string
  tradeType: string
  price: number
  endingCash: number
}
