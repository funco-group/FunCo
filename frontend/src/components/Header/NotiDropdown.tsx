import { useEffect, useState } from 'react'
import {
  NotiDropdownContainer,
  NotiHistoryContentContainer,
  NotiMoreButton,
} from '@/components/Header/NotiDropdown.styled'
import NotiHistoryType from '@/interfaces/notification/NotiHistoryType'
import NotiHistoryContent from '@/components/Header/NotiHistoryContent'
import { getNotiHistoryList, sendReadNotiList } from '@/apis/notification'
import useUserState from '@/hooks/recoilHooks/useUserState'

interface NotiDropdownProps {
  visible: boolean
}

function NotiDropdown({ visible }: NotiDropdownProps) {
  const [notiHistoryList, setNotiHistoryList] = useState<NotiHistoryType[]>()
  const [open, setOpen] = useState(false)
  const { updateUnReadNoti } = useUserState()

  useEffect(() => {
    if (visible) {
      setOpen(true)
      getNotiHistoryList((res) => {
        const { data } = res
        setNotiHistoryList(data)
      })
    } else {
      setTimeout(() => setOpen(false), 150)
    }
  }, [visible])

  useEffect(() => {
    if (notiHistoryList !== undefined) {
      sendReadNotiList(() => {
        updateUnReadNoti(0)
      })
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [notiHistoryList])

  if (!open) {
    return null
  }

  return (
    <NotiDropdownContainer $visible={visible}>
      <NotiHistoryContentContainer>
        {notiHistoryList ? (
          notiHistoryList.map((notiHistory) => (
            <NotiHistoryContent
              key={notiHistory.id}
              notiHistory={notiHistory}
            />
          ))
        ) : (
          <>노티없어영~~~</>
        )}
      </NotiHistoryContentContainer>
      <NotiMoreButton>
        <p>더보기</p>
        <img src="/icon/chevron-down.png" alt="more-icon" />
      </NotiMoreButton>
    </NotiDropdownContainer>
  )
}

export default NotiDropdown
