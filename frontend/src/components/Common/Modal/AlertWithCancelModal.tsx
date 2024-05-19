import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
} from '@/components/TradeHistory/Follow/Following/SettleModal.styled'
import { ReactNode } from 'react'
import { ContentDiv } from './AlertModal.styled'
import BrandButtonComponent from '../Button/BrandButtonComponent'

interface AlertWithCancelModalProps {
  title: string
  content: ReactNode
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
        <div className="mb-4 box-border w-full border-b border-l-0 border-r-0 border-t-0 border-solid border-b-borderGray p-4 font-NSB text-[1.1rem]">
          {title}
        </div>
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
