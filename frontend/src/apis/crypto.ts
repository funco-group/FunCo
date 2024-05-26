import { AxiosResponse } from 'axios'
import localAxios from '@/utils/http-commons'
import { FavoriteCoinResponseType } from '@/interfaces/PriceWindowType'

const domain = 'crypto'

export async function getFavoriteCoinList(
  success: (response: AxiosResponse<FavoriteCoinResponseType>) => void,
) {
  await localAxios.get(`v1/${domain}/favorite`).then(success)
}

export async function addFavoriteCoin(code: string) {
  await localAxios.post(`v1/${domain}/favorite`, {
    ticker: code,
  })
}

export async function removeFavoriteCoin(code: string) {
  await localAxios.delete(`v1/${domain}/favorite/${code}`)
}
