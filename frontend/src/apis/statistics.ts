import { AxiosResponse } from 'axios'
import localAxios from '@/utils/http-commons'
import { StatisticsType, StartDateType } from '@/interfaces/StatisticsType'

const domain = 'statistics'

export async function getDailyStatistics(
  year: string,
  month: string,
  success: (response: AxiosResponse<StatisticsType[]>) => void,
) {
  await localAxios
    .get(`/v1/${domain}/daily?year=${year}&month=${month}`)
    .then(success)
}

export async function getMonthlyStatistics(
  year: string,
  success: (response: AxiosResponse<StatisticsType[]>) => void,
) {
  await localAxios.get(`/v1/${domain}/monthly?year=${year}`).then(success)
}

export async function getStartDate(
  success: (response: AxiosResponse<StartDateType>) => void,
) {
  await localAxios.get(`/v1/${domain}/startdate`).then(success)
}

export async function getUserDailyStatistics(
  memerId: number,
  year: string,
  month: string,
  success: (response: AxiosResponse<StatisticsType[]>) => void,
) {
  await localAxios
    .get(`/v1/${domain}/daily/${memerId}?year=${year}&month=${month}`)
    .then(success)
}

export async function getUserMonthlyStatistics(
  memerId: number,
  year: string,
  success: (response: AxiosResponse<StatisticsType[]>) => void,
) {
  await localAxios
    .get(`/v1/${domain}/monthly/${memerId}?year=${year}`)
    .then(success)
}

export async function getUserStartDate(
  memerId: number,
  success: (response: AxiosResponse<StartDateType>) => void,
) {
  await localAxios.get(`/v1/${domain}/startdate/${memerId}`).then(success)
}
