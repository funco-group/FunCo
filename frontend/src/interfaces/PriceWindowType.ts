export interface ResMarketCodeType {
  market: string;
  korean_name: string;
  english_name: string;
}

export interface PriceType {
  code: string;
  koreanName: string;
  tradePrice: number;
  change: string;
  signedChangeRate: number;
  signedChangePrice: number;
  accTradeVolme24h: number;
  accTradePrice24h: number;
  highPrice: number;
  lowPrice: number;
  updated: boolean;
}

export interface FavoriteCoinResponseType {
  tickers: string[];
}

export interface HoldingCoinResponseType {
  holdingCoins: string[];
}

export interface WebSocketHandlers {
  ws: WebSocket | null;
  status: number | undefined;
  connect: () => void;
  onopen: () => void;
  send: (codes: string) => void;
  onmessage: (event: MessageEvent) => void;
  onerror: () => void;
  close: () => void;
}
