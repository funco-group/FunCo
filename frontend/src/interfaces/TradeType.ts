export interface TradeResultType {
  ticker: string
  volume: number
  price: number
}

export interface TradeListType {
  id: number
  tradeDate: string
  ticker: string
  tradeType: string
  volume: number
  orderCash: number
  price: number
}

export interface FuturesTradeType {
  ticker: string
  orderCash: number
  leverage: number
}

export interface FuturesType {
  id: number
  leverage: number
  orderCash: number
  price: number
  settlement: number
  ticker: string
  tradeDate: string
  tradeType: string
  liquidatedPrice: number
}
