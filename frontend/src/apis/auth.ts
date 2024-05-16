import { AxiosResponse } from 'axios'
import { UserType } from '@/interfaces/user/UserType'
import localAxios from '@/utils/http-commons'

const version = 'v1'
const domain = 'auth'

export async function postGoogleOAuth(
  success: (res: AxiosResponse<UserType>) => void,
  code: string | null,
) {
  localAxios
    .post(`/${version}/${domain}/google/signin?code=${code}`)
    .then(success)
}

export async function getTokenReissue() {
  localAxios.post(`/${version}/${domain}/reissue`).then((res) => {
    if (typeof window !== 'undefined') {
      const savedValue = localStorage.getItem('userInfo')
      const userInfo = savedValue ? JSON.parse(savedValue) : null
      if (userInfo && userInfo.user !== null) {
        const { data } = res
        console.log(data)
      }
    }
  })
}

export async function postLogout() {
  localAxios.post(`/${version}/${domain}/google/signout`)
}
