import { AxiosResponse } from "axios";
import { FollowerContentType } from "@/interfaces/tradeHistory/follow/FollowerContentType";
import localAxios from "@/utils/http-commons";
import { ResFollowingType } from "@/interfaces/tradeHistory/follow/ResFollowingType";
import { TradeListType } from "@/interfaces/TradeType";

interface FollowerListResType {
  last: boolean;
  followers: FollowerContentType[];
}

const domain = "follows";
const version = "v1";

export async function getFollowerList(
  success: (res: AxiosResponse<FollowerListResType>) => void,
  settled: "all" | "following" | "settled",
) {
  await localAxios
    .get(`/${version}/${domain}/follower?settled=${settled}`)
    .then(success);
}

export async function addFollow(
  body: {
    memberId: number;
    investment: number;
  },
  success: () => void,
) {
  await localAxios.post(`/${version}/${domain}`, body).then(success);
}

export async function getFollowingList(
  success: (res: AxiosResponse<ResFollowingType>) => void,
) {
  await localAxios.get(`/${version}/${domain}/following`).then(success);
}

export async function getFollowingTradeList(
  followId: number,
  page: number,
  size: number,
  success: (response: AxiosResponse<TradeListType[]>) => void,
) {
  await localAxios
    .get(`/${version}/${domain}/${followId}/trades?page=${page}&size=${size}`)
    .then(success);
}

export async function removeFollow(followId: number, success: () => void) {
  await localAxios.patch(`/${version}/${domain}/${followId}`).then(success);
}
