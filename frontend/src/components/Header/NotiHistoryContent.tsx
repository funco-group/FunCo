import NotiHistoryType from '@/interfaces/notification/NotiHistoryType'
import {
  NotiHistoryContentContainer,
  NotiHistoryContentTitleP,
  NotiHistoryContentMsgP,
  NotiHistoryContentDateP,
} from '@/components/Header/NotiHistoryContent.styled'
import parseDate from '@/utils/parseDate'

interface NotiHistoryContentProps {
  notiHistory: NotiHistoryType
}

function NotiHistoryContent({ notiHistory }: NotiHistoryContentProps) {
  const notificationTypeMap = new Map([
    ['BUY', '매수 알림'],
    ['SELL', '매도 알림'],
    ['SETTLE', '정산 알림'],
    ['FORCE_SETTLE', '강제 정산 알림'],
    ['FOLLOW', '팔로우 알림'],
  ])

  const time = parseDate(notiHistory.notificationDate)

  return (
    <NotiHistoryContentContainer $isRead={notiHistory.isRead}>
      <NotiHistoryContentTitleP>
        {notificationTypeMap.get(notiHistory.notificationType)}
      </NotiHistoryContentTitleP>
      <NotiHistoryContentMsgP>{notiHistory.message}</NotiHistoryContentMsgP>
      <NotiHistoryContentDateP>{time}</NotiHistoryContentDateP>
    </NotiHistoryContentContainer>
  )
}

export default NotiHistoryContent
