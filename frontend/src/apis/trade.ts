import { AxiosResponse } from "axios";
import localAxios from "@/utils/http-commons";
import { HoldingCoinResponseType } from "@/interfaces/PriceWindowType";
import { TradeResultType, TradeListType } from "@/interfaces/TradeType";
import { AssetHistoryType } from "@/interfaces/AssetType";

const domain = "trade";
const version = "v1";

export async function getHoldingCoin(
  success: (response: AxiosResponse<HoldingCoinResponseType>) => void,
) {
  await localAxios.get(`/${version}/${domain}/holding`).then(success);
}

export async function buyMarket(
  ticker: string,
  orderCash: number,
  success: (response: AxiosResponse<TradeResultType>) => void,
  error: (response: any) => void,
) {
  await localAxios
    .post(`/${version}/${domain}/market-buying`, {
      ticker,
      orderCash,
    })
    .then(success)
    .catch(error);
}

export async function sellMarket(
  ticker: string,
  volume: number,
  success: (response: AxiosResponse<TradeResultType>) => void,
  error: (response: any) => void,
) {
  await localAxios
    .post(`/${version}/${domain}/market-selling`, {
      ticker,
      volume,
    })
    .then(success)
    .catch(error);
}

export async function buyLimit(
  ticker: string,
  volume: number,
  price: number,
  success: (response: AxiosResponse<TradeResultType>) => void,
  error: (response: any) => void,
) {
  await localAxios
    .post(`/${version}/${domain}/limit-buying`, {
      ticker,
      volume,
      price,
    })
    .then(success)
    .catch(error);
}

export async function sellLimit(
  ticker: string,
  volume: number,
  price: number,
  success: (response: AxiosResponse<TradeResultType>) => void,
  error: (response: any) => void,
) {
  await localAxios
    .post(`/${version}/${domain}/limit-selling`, {
      ticker,
      volume,
      price,
    })
    .then(success)
    .catch(error);
}

export async function getTradeList(
  ticker: string,
  page: number,
  size: number,
  success: (response: AxiosResponse<TradeListType[]>) => void,
) {
  await localAxios
    .get(
      `/${version}/${domain}/orders?ticker=${ticker}&page=${page}&size=${size}`,
    )
    .then(success);
}

export async function getOpenTradeList(
  ticker: string,
  page: number,
  size: number,
  success: (response: AxiosResponse<TradeListType[]>) => void,
) {
  await localAxios
    .get(
      `/${version}/${domain}/open-orders?ticker=${ticker}&page=${page}&size=${size}`,
    )
    .then(success);
}

export async function getAllOpenTradeList(
  page: number,
  size: number,
  success: (response: AxiosResponse<TradeListType[]>) => void,
) {
  await localAxios
    .get(`/${version}/${domain}/open-orders?page=${page}&size=${size}`)
    .then(success);
}

export async function cancleOrder(id: number, success: () => void) {
  await localAxios
    .delete(`/${version}/${domain}/open-orders/${id}`)
    .then(success);
}

export async function getUserTradeList(
  memberId: number,
  page: number,
  size: number,
  success: (response: AxiosResponse<AssetHistoryType[]>) => void,
) {
  await localAxios
    .get(`/${version}/${domain}/orders/${memberId}?&page=${page}&size=${size}`)
    .then(success);
}
