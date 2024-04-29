import { AxiosResponse } from "axios";
import localAxios from "@/utils/http-commons";
import { CashType } from "@/interfaces/common/AssetType";
import {
  AssetHistoryType,
  AssetResponseType,
  CoinVolumeType,
} from "@/interfaces/AssetType";

const domain = "asset";
const version = "v1";

export async function getCash(
  success: (response: AxiosResponse<CashType>) => void,
) {
  await localAxios.get(`/${version}/${domain}/cash`).then(success);
}

export async function getCoinVolume(
  code: string,
  success: (response: AxiosResponse<CoinVolumeType>) => void,
) {
  await localAxios.get(`${version}/${domain}/crypto/${code}`).then(success);
}

export async function getAsset(
  success: (response: AxiosResponse<AssetResponseType>) => void,
) {
  await localAxios.get(`/${version}/${domain}`).then(success);
}

export async function getHistory(
  success: (response: AxiosResponse<AssetHistoryType[]>) => void,
) {
  await localAxios.get(`/${version}/${domain}/history`).then(success);
}

export async function getUserAsset(
  memberId: number,
  success: (response: AxiosResponse<AssetResponseType>) => void,
) {
  await localAxios.get(`/${version}/${domain}/${memberId}`).then(success);
}
