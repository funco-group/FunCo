export interface FollowingType {
  followId: number;
  nickname: string;
  investment: number;
  followedAt: string;
  cash: number;
  coins: {
    ticker: string;
    volume: number;
  }[];
}
