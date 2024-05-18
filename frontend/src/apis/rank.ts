import { AxiosResponse } from 'axios'
import { ResRankType } from '@/interfaces/rank/ResRankType'
import localAxios from '@/utils/http-commons'

const domain = 'rank'

// eslint-disable-next-line import/prefer-default-export
export async function getRankList(
  type: string,
  page: number,
  success: (res: AxiosResponse<ResRankType>) => void,
) {
  await localAxios.get(`/v1/${domain}?type=${type}&page=${page}`).then(success)
}
