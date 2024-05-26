import { AxiosResponse } from 'axios'
import localAxios from '@/utils/http-commons'
import {
  AssetResponseType,
  CoinAssetType,
  FollowAssetType,
  FuturesAssetType,
  PortfolioAssetType,
} from '@/interfaces/AssetType'

const domain = 'asset'

export async function getAsset(
  success: (response: AxiosResponse<AssetResponseType>) => void,
) {
  await localAxios.get(`/v1/${domain}`).then(success)
}

export async function getHistory(
  period: string,
  asset: string,
  trade: string,
  success: (
    response: AxiosResponse<
      | CoinAssetType[]
      | FuturesAssetType[]
      | FollowAssetType[]
      | PortfolioAssetType[]
    >,
  ) => void,
) {
  await localAxios
    .get(`/v1/${domain}/history?period=${period}&asset=${asset}&trade=${trade}`)
    .then(success)
}

export async function getUserAsset(
  memberId: number,
  success: (response: AxiosResponse<AssetResponseType>) => void,
) {
  await localAxios.get(`/v1/${domain}/${memberId}`).then(success)
}

export async function patchInitCash(success: () => void) {
  await localAxios.patch(`/v1/${domain}/init-cash`).then(success)
}
