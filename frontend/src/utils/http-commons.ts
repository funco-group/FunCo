import axios from 'axios'
import httpStatusCode from './http-status'

const localAxios = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  },
  withCredentials: true,
})

localAxios.interceptors.request.use(
  (config) => {
    if (config.url?.includes('/v1/auth')) {
      return config
    }
    if (typeof window !== 'undefined') {
      const savedValue = localStorage.getItem('userInfo')
      const userInfo = savedValue ? JSON.parse(savedValue) : null
      if (userInfo && userInfo.user !== null) {
        const newConfig = { ...config }
        newConfig.headers.Authorization = `Bearer ${userInfo.user.accessToken}`
        return newConfig
      }
    }
    return config
  },
  (error) => Promise.reject(error),
)

localAxios.interceptors.response.use(
  (response) => response,
  async (error) => {
    console.log(error.config.url)
    if (
      error.response.status === httpStatusCode.UNAUTHORIZED &&
      error.response.data.errorCode === 'EXPIRED_TOKEN' &&
      error.config.url !== '/v1/auth/reissue'
    ) {
      localAxios.post(`/v1/auth/reissue`).then((res) => {
        if (typeof window !== 'undefined') {
          const savedValue = localStorage.getItem('userInfo')
          const userInfo = savedValue ? JSON.parse(savedValue) : null
          if (userInfo && userInfo.user !== null) {
            const { data } = res
            console.log(data)
          }
        }
      })
      // GetTokenReissue()
      // error.config.headers = {
      //     "Content-Type": "application/json",
      //     Authorization: `Bearer ${sessionStorage.getItem("accessToken")}`,
      // };
      // const response = await axios.request(error.config)
      // console.log(error)
      // return response
    }
    return Promise.reject(error)
  },
)

export default localAxios
