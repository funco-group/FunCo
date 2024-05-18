import { AxiosResponse } from 'axios'
import { MemberType } from '@/interfaces/userPage/MemberType'
import localAxios from '@/utils/http-commons'
import { CashType } from '@/interfaces/common/AssetType'

const domain = 'members'

export async function getCash(
  success: (response: AxiosResponse<CashType>) => void,
) {
  await localAxios.get(`/v1/${domain}/cash`).then(success)
}

export async function getMemberInfo(
  memberId: number,
  success: (res: AxiosResponse<MemberType>) => void,
) {
  await localAxios.get(`/v2/${domain}/${memberId}`).then(success)
}

export async function getMyInfo(
  success: (res: AxiosResponse<MemberType>) => void,
) {
  await localAxios.get(`/v1/${domain}/mypage`).then(success)
}

export async function editNickname(nickname: string, success: () => void) {
  await localAxios
    .patch(`/v1/${domain}/nickname`, {
      nickname,
    })
    .then(success)
}

export async function editIntroduction(introduction: string) {
  await localAxios.patch(`/v1/${domain}/introduction`, {
    introduction,
  })
}
export async function buyPortfolio(sellerId: number, success: () => void) {
  await localAxios
    .post(`/v1/${domain}/portfolio/subscribe`, {
      sellerId,
    })
    .then(success)
}

export async function setPortfolio(
  portfolioStatus: string,
  portfolioPrice: number,
  success: () => void,
) {
  await localAxios
    .patch(`/v1/${domain}/portfolio`, {
      portfolioStatus,
      portfolioPrice,
    })
    .then(success)
}
