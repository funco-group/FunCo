import axios, { AxiosResponse } from 'axios'
import { ResMarketCodeType } from '@/interfaces/PriceWindowType'
import { ResTickerType } from '@/interfaces/tradeHistory/follow/ResTickerType'
import { CandleType } from '@/interfaces/CryptoType'

const url = 'https://api.upbit.com'

export async function getCoinList(
  success: (response: AxiosResponse<ResMarketCodeType[]>) => void,
) {
  await axios.get(`${url}/v1/market/all`).then(success)
}

export async function getTickerPrice(
  markets: string,
  success: (response: AxiosResponse<ResTickerType[]>) => void,
) {
  await axios.get(`${url}/v1/ticker?markets=${markets}`).then(success)
}

export function getMinuteCandle(
  unit: number,
  market: string,
  count: number,
  success: (response: AxiosResponse<CandleType[]>) => void,
) {
  return axios
    .get(`${url}/v1/candles/minutes/${unit}?market=${market}&count=${count}`)
    .then(success)
}

export function getDaysCandle(
  type: string,
  market: string,
  count: number,
  success: (response: AxiosResponse<CandleType[]>) => void,
) {
  return axios
    .get(`${url}/v1/candles/${type}?market=${market}&count=${count}`)
    .then(success)
}
