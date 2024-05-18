export interface FollowingType {
  member: {
    id: number
    nickname: string
    profileUrl: string
  }
  followId: number
  investment: number
  followedAt: string
  cash: number
  coins: {
    ticker: string
    volume: number
  }[]
}
