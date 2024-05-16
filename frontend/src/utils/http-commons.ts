import axios, { InternalAxiosRequestConfig } from 'axios'
import httpStatusCode from './http-status'

const baseURL = process.env.NEXT_PUBLIC_BASE_URL

const localAxios = axios.create({
  baseURL,
  withCredentials: true,
})

const setAuthorizationHeader = (config: InternalAxiosRequestConfig<any>) => {
  const savedValue = localStorage.getItem('userInfo')
  const userInfo = savedValue ? JSON.parse(savedValue) : null

  if (userInfo && userInfo.user) {
    const newConfig = { ...config }
    newConfig.headers.Authorization = `Bearer ${userInfo.user.accessToken}`
    return newConfig
  }
  return config
}

const setContentTypeHeader = (config: InternalAxiosRequestConfig<any>) => {
  const newConfig = { ...config }
  newConfig.headers['Content-Type'] = newConfig.url?.includes('/v1/notes/image')
    ? 'multipart/form-data'
    : 'application/json;charset=utf-8'
  return newConfig
}

localAxios.interceptors.request.use(
  (config) => {
    if (typeof window !== 'undefined') {
      let newConfig = { ...config }
      newConfig = setAuthorizationHeader(newConfig)
      newConfig = setContentTypeHeader(newConfig)
      return newConfig
    }
    return config
  },
  (error) => Promise.reject(error),
)

const handleTokenReissue = async (error: any) => {
  try {
    const response = await localAxios.post(`/v1/auth/reissue`)
    const { accessToken } = response.data

    const savedValue = localStorage.getItem('userInfo')
    const userInfo = savedValue ? JSON.parse(savedValue) : null

    if (userInfo && userInfo.user) {
      userInfo.user.accessToken = accessToken
      localStorage.setItem('userInfo', JSON.stringify(userInfo))

      const newError = { ...error }
      newError.config.headers.Authorization = `Bearer ${accessToken}`
      newError.config.headers['Content-Type'] = newError.config.url.includes(
        '/v1/notes/image',
      )
        ? 'multipart/form-data'
        : 'application/json;charset=utf-8'

      return await axios.request(newError.config)
    }
  } catch (reissueError) {
    return Promise.reject(reissueError)
  }

  return Promise.reject(error)
}

localAxios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (
      error.response.status === httpStatusCode.UNAUTHORIZED &&
      error.response.data.errorCode === 'EXPIRED_TOKEN' &&
      error.config.url !== '/v1/auth/reissue'
    ) {
      return handleTokenReissue(error)
    }

    return Promise.reject(error)
  },
)

export default localAxios
