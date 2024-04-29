export interface TradeResultType {
  ticker: string;
  volume: number;
  price: number;
}

export interface TradeListType {
  id: number;
  tradeDate: string;
  ticker: string;
  tradeType: string;
  volume: number;
  orderCash: number;
  price: number;
}
