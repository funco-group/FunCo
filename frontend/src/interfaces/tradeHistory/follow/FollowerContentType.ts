export interface FollowerContentType {
  followId: number;
  followedAt: string;
  nickname: string;
  investment: number;
  settlement: number | null;
  returnRate: number | null;
  commission: number | null;
  settleDate: string | null;
}
