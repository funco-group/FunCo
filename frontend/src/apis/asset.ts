import { AxiosResponse } from 'axios'
import localAxios from '@/utils/http-commons'
import {
  AssetHistoryType,
  AssetResponseType,
  CoinVolumeType,
} from '@/interfaces/AssetType'

const domain = 'asset'

export async function getAsset(
  success: (response: AxiosResponse<AssetResponseType>) => void,
) {
  await localAxios.get(`/v1/${domain}`).then(success)
}

export async function getHistory(
  success: (response: AxiosResponse<AssetHistoryType[]>) => void,
) {
  await localAxios.get(`/v1/${domain}/history`).then(success)
}

export async function getUserAsset(
  memberId: number,
  success: (response: AxiosResponse<AssetResponseType>) => void,
) {
  await localAxios.get(`/v1/${domain}/${memberId}`).then(success)
}
