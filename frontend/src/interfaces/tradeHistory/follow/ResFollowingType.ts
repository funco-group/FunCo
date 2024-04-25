import { FollowingType } from "./FollowingTyps";

export interface ResFollowingType {
  last: boolean;
  totalAsset: number;
  followings: FollowingType[];
}
