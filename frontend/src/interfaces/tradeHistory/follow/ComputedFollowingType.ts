export interface ComputedFollowingType {
  followId: number;
  nickname: string;
  date: string;
  investment: number;
  estimatedValue: number;
  cash: number;
  coins: {
    ticker: string;
    price: number;
  }[];
}
