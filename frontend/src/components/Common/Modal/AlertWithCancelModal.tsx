import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
} from '@/components/TradeHistory/Follow/Following/SettleModal.styled'
import { ContentDiv, TitleDiv } from './AlertModal.styled'
import BrandButtonComponent from '../Button/BrandButtonComponent'

interface AlertWithCancelModalProps {
  title: string
  content: string
  cancelAlert: () => void
  confirmAlert: () => void
}

function AlertWithCancelModal({
  title,
  content,
  cancelAlert,
  confirmAlert,
}: AlertWithCancelModalProps) {
  return (
    <SettleModalBackgroundContainer>
      <SettleModalContainer>
        <TitleDiv>{title}</TitleDiv>
        <ContentDiv>{content}</ContentDiv>
        <SettleModalContentButtonRowDiv>
          <BrandButtonComponent
            content="취소"
            color={null}
            cancel
            onClick={cancelAlert}
            disabled={false}
          />
          <BrandButtonComponent
            content="확인"
            color={null}
            cancel={false}
            onClick={confirmAlert}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  )
}

export default AlertWithCancelModal
