import { EventSourcePolyfill } from 'event-source-polyfill'
import { useEffect } from 'react'
import { toast } from 'react-toastify'
import useUserState from './recoilHooks/useUserState'

interface ResMessageType {
  unReadCount: number
  message: string
  notificationDate: string
}

function useSSE() {
  const { user, updateUnReadNoti } = useUserState()
  useEffect(() => {
    let eventSource: EventSourcePolyfill

    const fetchSSE = () => {
      if (!user) {
        return
      }
      const { accessToken } = user

      eventSource = new EventSourcePolyfill(
        `${process.env.NEXT_PUBLIC_BASE_URL}/v1/notifications/subscribe`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
          withCredentials: true,
        },
      )

      eventSource.onopen = () => {
        console.log('연결됐어요')
      }

      eventSource.onmessage = async (e) => {
        const res = (await e.data) as string
        const parseRes = res.replace(/\\/g, '')
        const parseParseRes = parseRes.slice(1, -1)
        const parsedData = JSON.parse(parseParseRes) as ResMessageType

        const newUnReadCount =
          parsedData.unReadCount > 99 ? 99 : parsedData.unReadCount

        updateUnReadNoti(newUnReadCount)

        toast(parsedData.message)
      }

      eventSource.onerror = (e) => {
        console.log('Error', e)
        eventSource.close()
        // 재연결 로직: 1초 후에 다시 시도
        setTimeout(fetchSSE, 1000)
      }
    }

    fetchSSE()

    // 컴포넌트 언마운트 시 연결 종료
    return () => {
      if (eventSource) {
        eventSource.close()
      }
    }
  }, []) // 의존성 배열이 비어 있으므로, 마운트될 때 한 번만 실행됩니다.
}

export default useSSE
