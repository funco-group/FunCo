import { RankMemberType } from "./RankMemberType";

export interface RankType {
  rank: number;
  member: RankMemberType;
  returnRate: number;
  totalAsset: number;
  followingAsset: number;
}
