export interface FollowerContentType {
  member: {
    id: number
    nickname: string
    profileUrl: string
  }
  followId: number
  followedAt: string
  investment: number
  settlement: number | null
  returnRate: number | null
  commission: number | null
  settleDate: string | null
}
