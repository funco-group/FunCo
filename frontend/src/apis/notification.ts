import { AxiosResponse } from 'axios'
import NotiHistoryType from '@/interfaces/notification/NotiHistoryType'
import localAxios from '@/utils/http-commons'

const domain = 'notifications'

export async function getNotiHistoryList(
  success: (res: AxiosResponse<NotiHistoryType[]>) => void,
) {
  await localAxios.get(`/v1/${domain}?size=10`).then(success)
}

export async function sendReadNotiList(success: () => void) {
  await localAxios.patch(`/v1/${domain}/read`).then(success)
}
